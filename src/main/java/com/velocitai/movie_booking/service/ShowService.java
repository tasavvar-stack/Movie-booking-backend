package com.velocitai.movie_booking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.model.Show;
import com.velocitai.movie_booking.model.Theater;

@Service
public interface ShowService {

	//public ResponseEntity<Show> saveShow(Long movieId, Long theaterId, Show show);

	public ResponseEntity<Show> findShowById(long id);

	public ResponseEntity<?> deleteShow(Long  id);

	public ResponseEntity<List<Show>> findAllShow();

	public ResponseEntity<List<Show>> findShowByMovieName(String movieName);

	public ResponseEntity<List<Show>> findShowByLocation(String location);

	public ResponseEntity<List<Show>> findShowByDate(LocalDate date);

	public ResponseEntity<List<Show>> findShowByTheater(long theaterId);


	public ResponseEntity<Show> saveShow(long movieId, long theaterId, Show show);
   public 	ResponseEntity<Show> UpdateShow( Show show);

//    public  ResponseEntity<Show> saveShow(Show show);

	 ResponseEntity<Show> saveShow(Show show, long theaterId, long movieId);


}
