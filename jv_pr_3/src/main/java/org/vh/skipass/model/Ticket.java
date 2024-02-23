package org.vh.skipass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private String id;
    private boolean active;
    private LocalDateTime issuedDate;
    private LocalDate validFrom;
    private LocalDate validTill;
    @Enumerated(EnumType.STRING)
    private TimeType timeType;
    @Enumerated(EnumType.STRING)
    private DayType dayType;
    private int maxEntrancesCount;
    private int maxDaysCount;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TicketEntrance> entrances;

    @Transient
    private int usedDaysCount;
}
