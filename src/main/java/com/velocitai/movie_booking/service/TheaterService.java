package com.velocitai.movie_booking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.model.Theater;
@Service
public interface TheaterService {

	public ResponseEntity<Theater> saveTheater(Theater Theater);
	
	public ResponseEntity<Theater> findTheaterById(long id);

	public ResponseEntity<Theater> updateTheater(Theater Theater);

	public ResponseEntity<?> deleteTheater(long id);

	public ResponseEntity<?> findAllTheater();

	public ResponseEntity<?> findTheaterByMovieName(String movieName);
	
	public ResponseEntity<?> findTheaterByLocation(String location);

	

	
}
