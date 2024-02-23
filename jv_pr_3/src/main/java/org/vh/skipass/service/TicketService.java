package org.vh.skipass.service;

import org.vh.skipass.model.DayType;
import org.vh.skipass.model.PassInfo;
import org.vh.skipass.model.Ticket;
import org.vh.skipass.model.TimeType;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {
    Ticket find(String id);

    List<Ticket> findAll();

    String create(LocalDateTime now, DayType dayType, TimeType timeType, int maxDaysCount, int maxEntrancesCount);

    void activate(String id);

    void deactivate(String id);

    PassInfo pass(LocalDateTime now, String id);
}
