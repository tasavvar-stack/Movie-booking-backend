package com.velocitai.movie_booking.controller;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/movies")
@CrossOrigin
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "Add a new movie", description = "Saves a new movie in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created movie"),
        @ApiResponse(responseCode = "400", description = "Invalid movie data")
    })
    @PostMapping("/save")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @Operation(summary = "Update an existing movie", description = "Updates the details of an existing movie.",security = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated movie"),
        @ApiResponse(responseCode = "404", description = "Movie not found"),
        @ApiResponse(responseCode = "400", description = "Invalid movie data")
    })
    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        return movieService.UpdateMovie(movie);
    }

    @Operation(summary = "Find movie by ID", description = "Retrieves a movie by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie found"),
        @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<Movie> findMovie(@Parameter(description = "ID of the movie to be retrieved", required = true) @PathVariable long id) {
        return movieService.findMovieById(id);
    }

    @Operation(summary = "Delete a movie", description = "Deletes a movie by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted movie"),
        @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@Parameter(description = "ID of the movie to be deleted", required = true) @PathVariable long id) {
        return movieService.deleteMovie(id);
    }

    @Operation(summary = "Find all movies", description = "Retrieves a list of all movies.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of movies found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> findAllMovie() {
        return movieService.findAllMovie();
    }

    @Operation(summary = "Find movies by location", description = "Retrieves a list of movies available in a specific location.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of movies found"),
        @ApiResponse(responseCode = "404", description = "No movies found for the specified location")
    })
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Movie>> findMoviesByLocation(@Parameter(description = "Location to search for movies", required = true) @PathVariable String location) {
        return movieService.findMoviesByLocation(location);
    }

    @Operation(summary = "Upload movie image", description = "Uploads an image for a movie.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image or movie ID")
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadMovieImage(@RequestParam("movieImage") MultipartFile file,
            @RequestParam("movieid") long movieId) {
        // Implement your logic for image upload here
        return null;
    }

    @Operation(summary = "Get movie image", description = "Retrieves the image of a movie by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getMovieImage(@Parameter(description = "ID of the movie to retrieve its image", required = true) @PathVariable long id) throws MalformedURLException {
        // Implement your logic for getting movie image here
        return null;
    }
}
