package com.velocitai.movie_booking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.model.User;

@Service
public interface MovieService {

	public ResponseEntity<Movie> saveMovie(Movie movie);

	public ResponseEntity<Movie> findMovieById(long id);

	public ResponseEntity<Movie> UpdateMovie(Movie movie);

	public ResponseEntity<?> deleteMovie(long id);

	public ResponseEntity<List<Movie>> findAllMovie();

	public ResponseEntity<List<Movie>> findMoviesByLocation(String location);

	public ResponseEntity<?> saveMovieImage(Movie user, MultipartFile file);

	public ResponseEntity<?> getMovieImage(long id);

	public ResponseEntity<?> findMoviesByTheaterId(long theaterId);

}
