package com.velocitai.movie_booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velocitai.movie_booking.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}
