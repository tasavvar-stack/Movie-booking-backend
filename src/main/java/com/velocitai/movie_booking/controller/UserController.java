package com.velocitai.movie_booking.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.model.User;
import com.velocitai.movie_booking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get authenticated user",
               description = "Returns the details of the currently authenticated user.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Get all users",
               description = "Retrieves a list of all users.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Update user",
               description = "Updates the details of a user.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/{User}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User object with updated details", required = true) 
            @RequestBody User user) {
        try {
            ResponseEntity<User> updatedUser = userService.UpdateUser(user);
            return updatedUser; // Return 200 OK with the updated user
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if user not found
        }
    }

    @Operation(summary = "Delete user",
               description = "Deletes a user by providing the user object.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        return userService.deleteUser(user); // Call the service with the User object
    }

    @Operation(summary = "Upload user image",
               description = "Uploads an image for the authenticated user.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/{id}/image")
    public ResponseEntity<?> uploadUserImage(
            @Parameter(description = "User object", required = true) 
            @RequestParam User user, 
            @RequestParam("image") MultipartFile file) {
        try {
            return userService.saveUserImage(user, file);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while uploading image: " + e.getMessage(), 
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value("${project.image}")
    private String path;

    @Operation(summary = "Upload a single image",
               description = "Uploads a single image to the server.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String uploadDir = "uploads/";
        ResponseEntity<String> fileName = userService.uploadImage(path, image);
        return ResponseEntity.ok("Image uploaded successfully: " + fileName);
    }

    @Operation(summary = "Upload multiple images",
               description = "Uploads multiple images to the server.",
               security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok("Image uploaded successfully: " + image);
    }
}
