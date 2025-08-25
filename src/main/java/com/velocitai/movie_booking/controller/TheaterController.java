package com.velocitai.movie_booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.service.TheaterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/theater")
@CrossOrigin
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @Operation(summary = "Get theaters by address", 
               description = "Retrieves a list of theaters based on the given address.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theaters retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No theaters found for the given address")
    })
    @GetMapping("/gettheaters/{address}")
    public ResponseEntity<?> searchByAddress(
            @Parameter(description = "The address to search for theaters", required = true)
            @PathVariable String address) {
        return theaterService.findTheaterByLocation(address);
    }

    @Operation(summary = "Delete a theater", 
               description = "Deletes a theater by its ID.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Theater deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTheater(
            @Parameter(description = "ID of the theater to delete", required = true) 
            @PathVariable Long id) {
        return theaterService.deleteTheater(id);
    }

    @Operation(summary = "Update a theater", 
               description = "Updates the details of an existing theater.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theater updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid theater data"),
        @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @PutMapping("/update")
    public ResponseEntity<Theater> updateTheater(
            @Parameter(description = "Updated theater data", required = true) 
            @RequestBody Theater theater) {
        return theaterService.updateTheater(theater);
    }

    @Operation(summary = "Add a new theater", 
               description = "Creates a new theater.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Theater created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid theater data")
    })
    @PostMapping("/save")
    public ResponseEntity<Theater> addTheater(
            @Parameter(description = "Theater data to create", required = true) 
            @RequestBody Theater theater) {
        return theaterService.saveTheater(theater);
    }

    @Operation(summary = "Get theater by ID", 
               description = "Retrieves the details of a theater by its ID.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theater retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(
            @Parameter(description = "ID of the theater to retrieve", required = true) 
            @PathVariable long id) {
        return theaterService.findTheaterById(id);
    }

    @Operation(summary = "Get all theaters", 
               description = "Retrieves a list of all theaters.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All theaters retrieved successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllTheaters() {
        return theaterService.findAllTheater();
    }

    @Operation(summary = "Find theater by location", 
               description = "Retrieves theaters based on the specified location.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Theaters retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No theaters found for the given location")
    })
    @GetMapping("/location/{location}")
    public ResponseEntity<?> findTheaterByLocation(
            @Parameter(description = "Location to search for theaters", required = true) 
            @PathVariable String location) {
        return theaterService.findTheaterByLocation(location);
    }
}
