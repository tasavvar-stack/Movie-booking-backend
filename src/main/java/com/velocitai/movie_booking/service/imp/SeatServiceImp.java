package com.velocitai.movie_booking.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.dao.SeatRepository;
import com.velocitai.movie_booking.model.Seat;
import com.velocitai.movie_booking.service.SeatService;

@Service
public class SeatServiceImp implements SeatService {

	@Autowired
	SeatRepository seatRepository;

	@Override
	public ResponseEntity<Seat> UpdateSeat(Seat seat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteSeat(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
