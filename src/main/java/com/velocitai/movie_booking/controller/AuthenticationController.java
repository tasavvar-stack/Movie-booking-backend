package com.velocitai.movie_booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velocitai.movie_booking.model.User;
import com.velocitai.movie_booking.security.AuthenticationService;
import com.velocitai.movie_booking.security.JwtService;
import com.velocitai.movie_booking.util.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthenticationController {
	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@Operation(summary = "Sign up a new user", description = "Used to register a new user by providing necessary details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully signed up"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "409", description = "User already exists") })
	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody @Valid User registerUserDto) {
		try {
			User registeredUser = authenticationService.signup(registerUserDto);
			return ResponseEntity.ok(registeredUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
		}
	}

	@Operation(summary = "Login", description = "Used to authenticate a user by email and password, returns a JWT token.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully authenticated and token generated"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(
			@Parameter(description = "Email address of the user", required = true, example = "user@example.com") @RequestParam String email,
			@Parameter(description = "Password of the user", required = true, example = "password123") @RequestParam String password) {
		try {
			User authenticatedUser = authenticationService.authenticate(email, password);

			String jwtToken = jwtService.generateToken(authenticatedUser);
			System.out.println(email);
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(jwtToken);
			loginResponse.setExpiresIn(jwtService.getExpirationTime());
			return ResponseEntity.ok(loginResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}
}
