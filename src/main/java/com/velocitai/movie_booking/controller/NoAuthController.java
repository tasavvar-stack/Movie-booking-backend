package com.velocitai.movie_booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.service.MovieService;
import com.velocitai.movie_booking.service.TheaterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/open")
@CrossOrigin
public class NoAuthController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    @Operation(summary = "Add a new theater", description = "Adds a new theater to the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theater successfully added"),
        @ApiResponse(responseCode = "400", description = "Invalid theater data provided"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/savee")
    public ResponseEntity<Theater> addTheater(@RequestBody Theater theater) {
        return theaterService.saveTheater(theater);
    }

    @Operation(summary = "Add a new movie", description = "Adds a new movie to the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie successfully added"),
        @ApiResponse(responseCode = "400", description = "Invalid movie data provided"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/saves")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @Operation(summary = "Update an existing movie", description = "Updates an existing movie in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid movie data provided"),
        @ApiResponse(responseCode = "404", description = "Movie not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/updates")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        return movieService.UpdateMovie(movie);
    }

    @Operation(summary = "Update an existing theater", description = "Updates an existing theater in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theater successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid theater data provided"),
        @ApiResponse(responseCode = "404", description = "Theater not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/updatee")
    public ResponseEntity<Theater> updateTheater(@RequestBody Theater theater) {
        return theaterService.updateTheater(theater);
    }

    @Operation(summary = "Get all theaters", description = "Returns a list of all theaters in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of theaters"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/cinemas/alls")
    public ResponseEntity<?> getAllTheaters() {
        return theaterService.findAllTheater();
    }

    @Operation(summary = "Get all movies", description = "Returns a list of all movies in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/movies/alls")
    public ResponseEntity<List<Movie>> findAllMovie() {
        return movieService.findAllMovie();
    }

    @Operation(summary = "Get movies by theater ID", description = "Returns a list of movies available in a specific theater based on theater ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies"),
        @ApiResponse(responseCode = "404", description = "Theater not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/location/{theaterId}")
    public ResponseEntity<?> findMoviesByTheaterId(
            @Parameter(description = "The ID of the theater", required = true, example = "1")
            @PathVariable long theaterId) {
        return movieService.findMoviesByTheaterId(theaterId);
    }

    @Operation(summary = "Get theaters by movie name", description = "Returns a list of theaters showing a specific movie based on the movie name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of theaters"),
        @ApiResponse(responseCode = "404", description = "Movie not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/cinemas/{movieName}")
    public ResponseEntity<?> findTheaterByMovieName(
            @Parameter(description = "The name of the movie", required = true, example = "Inception")
            @PathVariable String movieName) {
        return theaterService.findTheaterByMovieName(movieName);
    }
}
