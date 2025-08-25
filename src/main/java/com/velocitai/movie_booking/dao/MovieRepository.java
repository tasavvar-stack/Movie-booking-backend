package com.velocitai.movie_booking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.velocitai.movie_booking.model.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

//	@Query("select m from Movie m where m.")
//	List<Movie> findAllMoviesByTheaterId(long id);

	

}