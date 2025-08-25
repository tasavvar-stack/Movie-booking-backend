package com.velocitai.movie_booking.service.imp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.velocitai.movie_booking.dao.MovieRepository;
import com.velocitai.movie_booking.dao.TheaterRepository;
import com.velocitai.movie_booking.model.Seat;
import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.service.TheaterService;
import com.velocitai.movie_booking.util.City;
import com.velocitai.movie_booking.util.MovieDto;
import com.velocitai.movie_booking.util.TheaterDto;

@Service
public class TheaterServiceImp implements TheaterService {

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	MovieRepository movieRepository;
	@Override
	public ResponseEntity<Theater> findTheaterById(long id) {

		 Optional<Theater> theater = theaterRepository.findById(id);
	        return theater.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
	                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}


	@Override
	public ResponseEntity<?> findTheaterByMovieName(String movieName) {
		List<Theater> theaters = theaterRepository.findByShow_MovieName(movieName);
		List<TheaterDto> lists=new ArrayList<TheaterDto>();
		for(Theater theater:theaters) {
			
			TheaterDto theaterDto=new TheaterDto(theater);
			
//		theaterDto.setMovies( theater.getShowTimes().stream().map(movie -> movie.getMovie()).filter(movie -> movie.getMoviename().equals(movieName)).toList());
			theaterDto.setMovies( theater.getShowTimes().stream()
				    .filter(showTime -> showTime.getMovie().getMoviename().equals(movieName))  
				    .map(showTime -> {
				      MovieDto movieDto =  new MovieDto(showTime.getMovie());
				      movieDto.setShows(showTime.getMovie().getShows().stream()
				    		  .map(movie ->{
				    			  List<Seat> sortedSeat=movie.getSeat().stream().sorted(Comparator.comparing(Seat::getId))
				    					  .toList();
				    			  movie.setSeat(sortedSeat);
				    			  return movie;
				    			  
				    		  
				    		  })
	                            .collect(Collectors.toSet()));  
				      
	                        return movieDto;	
				    })
				    
				    .toList());
			lists.add(theaterDto);
		}
		
		return ResponseEntity.ok(lists);
	}

	@Override
	public ResponseEntity<?> findTheaterByLocation(String location) {
		List<Theater> theaters = theaterRepository.findByCity(City.valueOf(location.toUpperCase()));
		List<TheaterDto> lists=new ArrayList<TheaterDto>();
		for(Theater theater:theaters) {
			TheaterDto dto=new TheaterDto(theater);
			
		
//			dto.setMovies(theater.getShowTimes()
//					.stream().map((x)->new MovieDto(x.getMovie()))
//					.collect(Collectors.toMap(MovieDto::getId, movieDto ->movieDto
//							, (existing, replacement) -> existing))
//					.values()
//					.stream()
//					.toList());
			
			dto.setMovies(theater.getShowTimes()
	                .stream()
	                .collect(Collectors.toMap(
	                    showTime -> showTime.getMovie().getId(),  
	                    showTime -> { 
	                        MovieDto movieDto = new MovieDto(showTime.getMovie());
	                        movieDto.setShows(showTime.getMovie().getShows().stream()
	                            .filter(show -> show.getTheater().getId()==(theater.getId()))  
	                            .collect(Collectors.toSet()));  
	                        return movieDto;
	                    },
	                    (existing, replacement) -> { 
	                        existing.getShows().addAll(replacement.getShows());  
	                        return existing;
	                    }))
	                .values()
	                .stream()
	                .toList());
//			
			lists.add(dto);
		}
		
//		dto.setTheater(theaters);
		return new ResponseEntity<>(lists, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteTheater(long id) {
		if (theaterRepository.existsById(id)) {
			theaterRepository.deleteById(id);
			return ResponseEntity.ok("Theater deleted successfully.");
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Theater> updateTheater(Theater Theater) {
		if (theaterRepository.existsById(Theater.getId())) {
			Theater updatedTheater = theaterRepository.save(Theater);
			return new ResponseEntity<>(updatedTheater, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Theater> saveTheater(Theater Theater) {
		Theater savedTheater = theaterRepository.save(Theater);
		return new ResponseEntity<>(savedTheater, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> findAllTheater() {
		List<Theater> theaters = theaterRepository.findAll();
		List<TheaterDto> lists=new ArrayList<TheaterDto>();
		for(Theater theater:theaters) {
			TheaterDto dto=new TheaterDto(theater);
			
		
//			dto.setMovies(theater.getShowTimes()
//					.stream().map((x)->new MovieDto(x.getMovie()))
//					.collect(Collectors.toMap(MovieDto::getId, movieDto ->movieDto
//							, (existing, replacement) -> existing))
//					.values()
//					.stream()
//					.toList());
			
			dto.setMovies(theater.getShowTimes()
	                .stream()
	                .collect(Collectors.toMap(
	                    showTime -> showTime.getMovie().getId(),  
	                    showTime -> { 
	                        MovieDto movieDto = new MovieDto(showTime.getMovie());
	                        movieDto.setShows(showTime.getMovie().getShows().stream()
	                            .filter(show -> show.getTheater().getId()==(theater.getId()))
	                            .map(movie ->{
					    			  List<Seat> sortedSeat=movie.getSeat().stream().sorted(Comparator.comparing(Seat::getId))
					    					  .toList();
					    			  movie.setSeat(sortedSeat);
					    			  return movie;
					    			  
					    		  
					    		  })
	                            .collect(Collectors.toSet()));  
	                        return movieDto;
	                    },
	                    (existing, replacement) -> { 
	                        existing.getShows().addAll(replacement.getShows());  
	                        return existing;
	                    }))
	                .values()
	                .stream()
	                .toList());
//			
			lists.add(dto);
		}
		
//		dto.setTheater(theaters);
		return new ResponseEntity<>(lists, HttpStatus.OK);
	}
}



