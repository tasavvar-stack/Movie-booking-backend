package com.velocitai.movie_booking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("from Ticket t where t.user.id=?1")
	List<Ticket> findByUser(long id);
	
	
	 
}
