package org.vh.skipass.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vh.skipass.model.*;
import org.vh.skipass.repository.TicketRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

//    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketRepository ticketRepository;

    //    @PostConstruct
    public void init() {
        Ticket t = Ticket.builder()
                .id("xx")
                .issuedDate(LocalDateTime.now())
                .active(true)
                .validFrom(getSeasonStart(LocalDateTime.now()))
                .validTill(getSeasonEnd(LocalDateTime.now()))
                .dayType(DayType.WEEKEND)
                .timeType(TimeType.EVENING)
                .maxDaysCount(0)
                .maxEntrancesCount(10)
                .entrances(Collections.singletonList(TicketEntrance.builder()
                        .date(LocalDateTime.now())
                        .build()))
                .build();

        t.getEntrances().forEach(e -> e.setTicket(t)); // back reference

        ticketRepository.save(t);
    }

    @Override
    public Ticket find(String id) {
        final Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            log.warn("Ticket not found: {}", id);
        } else {
            log.debug("Ticket found: {}", id);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll().stream()
                .peek(t -> t.setUsedDaysCount(getUsedDays(t).size()))
                .collect(Collectors.toList());
    }

    @Override
    public String create(LocalDateTime now, DayType dayType, TimeType timeType, int maxDaysCount, int maxEntrancesCount) {
        Ticket ticket = Ticket.builder()
                .id(generateId())
                .issuedDate(now)
                .active(true)
                .validFrom(getSeasonStart(now))
                .validTill(getSeasonEnd(now))
                .timeType(timeType)
                .dayType(dayType)
                .maxDaysCount(maxDaysCount)
                .maxEntrancesCount(maxEntrancesCount)
                .build();

        ticketRepository.save(ticket);
        log.debug("New ticket has been created: {}", ticket.getId());

        return ticket.getId();
    }

    @Override
    public void activate(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket " + id + " not found"));
        ticket.setActive(true);
        ticketRepository.save(ticket);
        log.debug("Ticket has been activated: {}", ticket.getId());
    }

    @Override
    public void deactivate(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket " + id + " not found"));
        ticket.setActive(false);
        ticketRepository.save(ticket);
        log.debug("Ticket has been deactivated: {}", ticket.getId());
    }

    @Override
    public PassInfo pass(LocalDateTime now, String id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket == null) {
            return new PassInfo(false, "Ticket not found");
        }

        if (!ticket.isActive()) {
            return new PassInfo(false, "Ticket deactivated");
        }

        if (ticket.getValidFrom().atStartOfDay().isAfter(now)) {
            return new PassInfo(false, "Season not started yet");
        }

        if (ticket.getValidTill().plusDays(1).atStartOfDay().isBefore(now)) {
            return new PassInfo(false, "Season is over");
        }

        // out of max entrances count
        if (ticket.getMaxEntrancesCount() > 0 && ticket.getEntrances().size() >= ticket.getMaxEntrancesCount()) {
            return new PassInfo(false, "Ran out of max entrances count");
        }

        // out of max days count
        if (ticket.getMaxDaysCount() > 0) {
            final Set<LocalDate> days = getUsedDays(ticket);
            days.add(now.toLocalDate());

            if (days.size() > ticket.getMaxDaysCount()) {
                return new PassInfo(false, "Ran out of max days count");
            }
        }

        // mismatched day type
        if (ticket.getDayType() != DayType.ANY && ticket.getDayType() != getDayType(now)) {
            return new PassInfo(false, "Ticket can be used only on " + ticket.getDayType());
        }

        // mismatched time type
        final LocalDateTime startTime = getStartTime(now, ticket.getTimeType());
        final LocalDateTime endTime = getEndTime(now, ticket.getTimeType());
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_TIME;
            return new PassInfo(false, "Ticket can be used only from " +
                    startTime.format(f) + " till " + endTime.format(f));
        }

        // checks passed, save the entrance
        ticket.getEntrances().add(TicketEntrance.builder()
                .ticket(ticket)
                .date(LocalDateTime.now())
                .build());
        ticketRepository.save(ticket);

        return new PassInfo(true, getRemainingInfo(ticket));
    }

    private static Set<LocalDate> getUsedDays(Ticket ticket) {
        return ticket.getEntrances().stream()
                .map(t -> t.getDate().toLocalDate())
                .collect(Collectors.toSet());
    }

    private static String getRemainingInfo(Ticket ticket) {
        if (ticket.getMaxEntrancesCount() > 0) {
            return "Remaining entrances: " + (ticket.getMaxEntrancesCount() - ticket.getEntrances().size());
        }

        if (ticket.getMaxDaysCount() > 0) {
            return "Remaining days: " + (ticket.getMaxDaysCount() - getUsedDays(ticket).size() + 1);
        }

        return null;
    }

    private static LocalDateTime getStartTime(LocalDateTime time, TimeType timeType) {
        return switch (timeType) {
            case MORNING, ANY -> withTime(time, 9, 0);
            case EVENING -> withTime(time, 13, 0);
        };
    }

    private static LocalDateTime getEndTime(LocalDateTime time, TimeType timeType) {
        return switch (timeType) {
            case MORNING -> withTime(time, 13, 0);
            case EVENING, ANY -> withTime(time, 17, 0);
        };
    }

    private static LocalDateTime withTime(LocalDateTime time, int hours, int mins) {
        return time
                .with(ChronoField.MILLI_OF_DAY, 0) // reset
                .with(ChronoField.HOUR_OF_DAY, hours)
                .with(ChronoField.MINUTE_OF_HOUR, mins);
    }

    private static DayType getDayType(LocalDateTime time) {
        return switch (time.getDayOfWeek()) {
            // TODO: handle public holidays as weekends
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> DayType.WEEKDAY;
            case SATURDAY, SUNDAY -> DayType.WEEKEND;
        };
    }

    private static LocalDate getSeasonStart(LocalDateTime now) {
        // FIXME
        return LocalDate.of(2023, 12, 1);
    }

    private static LocalDate getSeasonEnd(LocalDateTime now) {
        // FIXME
        return LocalDate.of(2024, 3, 31);
    }

    private static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
