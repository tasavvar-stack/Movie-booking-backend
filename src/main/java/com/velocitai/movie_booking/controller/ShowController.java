package com.velocitai.movie_booking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velocitai.movie_booking.model.Show;
import com.velocitai.movie_booking.service.ShowService;
import com.velocitai.movie_booking.service.imp.ShowServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/show")
public class ShowController {
    
    @Autowired
    private ShowService showservice;

    @Autowired
    private ShowServiceImp showServiceImp;

    @Operation(summary = "Get shows by theater", 
               description = "Retrieves a list of shows available in a specific theater.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No shows found for the given theater")
    })
    @GetMapping("/byTheater")
    public ResponseEntity<List<Show>> getShowsByTheater(
            @Parameter(description = "ID of the theater", required = true) 
            @RequestParam("theaterId") long theaterId) {
        return showservice.findShowByTheater(theaterId);
    }
    
    @Operation(summary = "Get shows by date", 
               description = "Retrieves a list of shows available on a specific date.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No shows found for the given date")
    })
    @GetMapping("/byDate")
    public ResponseEntity<List<Show>> getShowsByDate(
            @Parameter(description = "Date for which to retrieve shows", required = true) 
            @RequestParam("date") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return showservice.findShowByDate(date);
    }

    @Operation(summary = "Save a new show", 
               description = "Saves a new show for a specific theater and movie.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Show created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid show data")
    })
    @PostMapping("/save/{theaterId}/{movieId}")
    public ResponseEntity<Show> saveShow(
            @Parameter(description = "Show details", required = true) 
            @RequestBody Show show,
            @Parameter(description = "ID of the theater", required = true) 
            @PathVariable long theaterId,
            @Parameter(description = "ID of the movie", required = true) 
            @PathVariable long movieId) {
        return showServiceImp.saveShow(show, theaterId, movieId);
    }

    @Operation(summary = "Delete a show", 
               description = "Deletes a specific show by ID.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Show deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Show not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShow(
            @Parameter(description = "ID of the show to be deleted", required = true) 
            @PathVariable Long id) {
        return showServiceImp.deleteShow(id);
    }
}
