package org.vh.skipass.repository;

import org.springframework.data.repository.CrudRepository;
import org.vh.skipass.model.Ticket;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, String> {
    List<Ticket> findAll();
}
