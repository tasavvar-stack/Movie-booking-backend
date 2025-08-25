package com.velocitai.movie_booking.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.model.Seat;
@Service
public interface SeatService {


	public ResponseEntity<Seat> UpdateSeat(Seat seat);

	public ResponseEntity<?> deleteSeat(long id);

	
}
