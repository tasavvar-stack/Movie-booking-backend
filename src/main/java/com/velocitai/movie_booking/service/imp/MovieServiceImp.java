package com.velocitai.movie_booking.service.imp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.dao.MovieRepository;
import com.velocitai.movie_booking.dao.ShowRepository;
import com.velocitai.movie_booking.dao.TheaterRepository;
import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.model.Show;
import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.service.MovieService;

@Service
public class MovieServiceImp implements MovieService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	ShowRepository showRepository;

	@Override
	public ResponseEntity<Movie> saveMovie(Movie movie) {

		try {
			// Save the movie object to the database
			Movie savedMovie = movieRepository.save(movie);

			// Return the saved movie with a status of CREATED
			return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
		} catch (Exception e) {
			// Log the error (optional)
			e.printStackTrace();

			// Return an error response with status INTERNAL_SERVER_ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@Override
	public ResponseEntity<Movie> findMovieById(long id) {
		Optional<Movie> optionalMovie = movieRepository.findById(id);
		if (optionalMovie.isPresent()) {
			return ResponseEntity.ok(optionalMovie.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<Movie> UpdateMovie(Movie movie) {

		if (movie.getId() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Optional<Movie> optionalExistingMovie = movieRepository.findById(movie.getId());
		if (!optionalExistingMovie.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		Movie existingMovie = optionalExistingMovie.get();
		existingMovie.setMoviename(movie.getMoviename());
		existingMovie.setGenre(movie.getGenre());
		existingMovie.setMovieLanguage(movie.getMovieLanguage());
		existingMovie.setDuration(movie.getDuration());
		existingMovie.setMovieImage(movie.getMovieImage());
		Movie updatedMovie = movieRepository.save(existingMovie);
		return ResponseEntity.ok(updatedMovie);
	}

	

	@Override
	public ResponseEntity<List<Movie>> findAllMovie() {
		List<Movie> movies = movieRepository.findAll();
		if (!movies.isEmpty()) {
			return ResponseEntity.ok(movies);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

	}

	@Override
	public ResponseEntity<List<Movie>> findMoviesByLocation(String location) {

		List<Theater> theaters = theaterRepository.findByAddressContainingIgnoreCase(location);
		/*
		 * System.out.println(theaters.get(1).getName()); Optional<Show> s =
		 * showRepository.findById(theaters.get(0).getId());
		 * System.out.println(s.get().getMovie().getMoviename());
		 */
		List<Movie> movieList = new ArrayList<>();

		if (!theaters.isEmpty()) {
			for (Theater theater : theaters) {
				List<Show> shows = showRepository.findByTheater(theater);
				if (!shows.isEmpty()) {
					for (Show show : shows) {
						Movie movies = show.getMovie();
						if (!movieList.contains(movies)) {
							movieList.add(movies);
						}
					}
				} else {
					System.out.println("No shows found for Theater ID " + theater.getId());
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		return ResponseEntity.ok(movieList);

	}

	@Override
	public ResponseEntity<?> saveMovieImage(Movie user, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getMovieImage(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteMovie(long id) {
		Movie movie = findMovieById(id).getBody();
		if (movie != null) {
			if (movie.getShows() != null) {
				showRepository.deleteAll(movie.getShows());
			}
			movieRepository.delete(movie);
		}
		return ResponseEntity.ok("message");
	}

	public ResponseEntity<?> findMoviesByTheaterId(long theaterId) {
		Optional<Theater> optional = theaterRepository.findById(theaterId);

		if(optional.isPresent()) {
		Theater theater=	optional.get();
		List<String> list= theater.getShowTimes().stream().map(x-> x.getMovie().getMovieImage()).toList();
		return ResponseEntity.status(HttpStatus.OK).body(list);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}