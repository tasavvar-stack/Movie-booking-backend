package com.velocitai.movie_booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velocitai.movie_booking.model.Ticket;
import com.velocitai.movie_booking.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Operation(summary = "Save a new ticket", 
               description = "Creates a new ticket for a movie show.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket data")
    })
    @PostMapping("/save")
    public ResponseEntity<Ticket> saveTicket(
            @Parameter(description = "Ticket data to create", required = true) 
            @RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    @Operation(summary = "Delete a ticket", 
               description = "Deletes a ticket by its ID.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<?> deleteTicket(
            @Parameter(description = "ID of the ticket to delete", required = true) 
            @PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }

    @Operation(summary = "Find all tickets", 
               description = "Retrieves a list of all tickets.", 
               security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All tickets retrieved successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<?> findAllTicket() {
        return ticketService.findAllTickets();
    }
}
