package org.vh.skipass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@ToString(exclude = {"ticket"})
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntrance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    @JsonIgnore
    private Ticket ticket;

    private LocalDateTime date;
}
