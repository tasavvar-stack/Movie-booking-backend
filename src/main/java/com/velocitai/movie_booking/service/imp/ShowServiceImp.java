package com.velocitai.movie_booking.service.imp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.dao.MovieRepository;
import com.velocitai.movie_booking.dao.SeatRepository;
import com.velocitai.movie_booking.dao.ShowRepository;
import com.velocitai.movie_booking.dao.TheaterRepository;
import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.model.Seat;
import com.velocitai.movie_booking.model.Show;
import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.service.ShowService;
import com.velocitai.movie_booking.util.SeatType;

@Service
public class ShowServiceImp implements ShowService {

	@Autowired
	ShowRepository showRepository;

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	TheaterRepository theaterRepository;

//	@Override
//	public ResponseEntity<Show> saveShow(Show show) {
//		 
//			  show.setDate(LocalDate.now());
//		        show.setTime(LocalTime.now());
//		        Show savedShow = showRepository.save(show);
//
//		       return ResponseEntity.ok(savedShow);
//	}

	@Override
	public ResponseEntity<Show> saveShow(Show show, long theaterId, long movieId) {
		Theater theater = theaterRepository.findById(theaterId).orElseThrow();
		Movie movie = movieRepository.findById(movieId).orElseThrow();
		show.setTheater(theater);
		show.setMovie(movie);
		
		List<Seat> seats = new ArrayList<Seat>();
		char c = 'A';
		for (char i = c; i <= 'E'; i++) {
			for (int j = 1; j <= 18; j++) {
				if (i > 'B') {
					Seat seat = new Seat();
					seat.setSeatNumber(i + "" + j);
					seat.setPrice(200);
					seat.setType(SeatType.REGULAR);
					seat.setBooked(false);
					
					seats.add(seat);
				} else {
					Seat seat = new Seat();
					seat.setSeatNumber(i + "" + j);
					seat.setPrice(400);
					seat.setType(SeatType.VIP);
					seat.setBooked(false);
					
					seats.add(seat);
				}
			}
		}

		show.setSeat(seats);
		seatRepository.saveAll(seats);
		Show savedShow = showRepository.save(show);
		// Using synchronized block to avoid ConcurrentModificationException
		
		    if (theater.getShowTimes() != null) {
		        theater.getShowTimes().add(show);
		    } else {
		        theater.setShowTimes(Arrays.asList(show));
		    }
		    if (theater.getMovies() != null) {
		        theater.getShowTimes().add(show);
		        theater.getMovies().add(movie);
		    } else {
		        theater.setShowTimes(Arrays.asList(show));
		        theater.setMovies(Arrays.asList(movie));
		    }
		    
			theaterRepository.save(theater);
		

		return ResponseEntity.ok(savedShow);
	}

	@Override
	public ResponseEntity<Show> findShowById(long id) {

		Optional<Show> showOptional = showRepository.findById(id);

		if (showOptional.isPresent()) {
			return ResponseEntity.ok(showOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Override
	public ResponseEntity<Show> UpdateShow(Show show) {
		Optional<Show> existingshowOptional = showRepository.findById(show.getId());
		if (existingshowOptional.isPresent()) {
			Show existingShow = existingshowOptional.get();
			existingShow.setDate(show.getDate());
			existingShow.setTime(show.getTime());
			existingShow.setMovie(show.getMovie());
			existingShow.setSeat(show.getSeat());
			existingShow.setTheater(show.getTheater());
			Show updateShow = showRepository.save(show);
			return ResponseEntity.ok(updateShow);
		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteShow(Long id) {

		Optional<Show> show = showRepository.findById(id);

		if (show.isPresent()) {

			showRepository.deleteById(id);
			return ResponseEntity.ok("Show deleted successfully.");
		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show not found.");
		}
	}

	@Override
	public ResponseEntity<List<Show>> findAllShow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Show>> findShowByMovieName(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Show>> findShowByLocation(String location) {

		return null;
	}

	@Override
	public ResponseEntity<List<Show>> findShowByDate(LocalDate date) {
		List<Show> shows = showRepository.findByDate(date);
		if (shows.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(shows, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Show>> findShowByTheater(long theaterId) {
		List<Show> shows = showRepository.findByTheaterId(theaterId);
		if (shows.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(shows, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Show> saveShow(long movieId, long theaterId, Show show) {
		// TODO Auto-generated method stub
		return null;
	}

}
