package com.velocitai.movie_booking.service.imp;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.dao.UserRepository;
import com.velocitai.movie_booking.model.User;
import com.velocitai.movie_booking.service.UserService;



@Service
public class UserServiceImp  implements UserService{
    @Autowired
    private  UserRepository userRepository;
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
	@Override
	public ResponseEntity<User> findUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	} 
	@Override
	public ResponseEntity<?> getUserImage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
		return null;
	}  
	    // Update User
	   @Override
	   public ResponseEntity<User> UpdateUser(User user){
	        Long id = user.getId();  
	        Optional<User> optionalUser = userRepository.findById(id);
	        if (optionalUser.isPresent()) {
	            User existingUser = optionalUser.get();
	            // existingUser.setName(user.getName());
	            // existingUser.setEmail(user.getEmail()); 
	            // existingUser.setPassword(user.getPassword());    
	            User updatedUser = userRepository.save(existingUser);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK); 
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	        }
	    }  
	      @Override
	   public ResponseEntity<User> deleteUser(User user) {
	        Long id = user.getId();  // Extract the ID from the User object
	        Optional<User> optionalUser = userRepository.findById(id);
	        if (optionalUser.isPresent()) { 
	            userRepository.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.OK);   // Return 200 OK
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 Not Found
	        }
	    }    
	      private static final String UPLOAD_DIR = "uploads/";

	         @Override
	      public ResponseEntity<User> saveUserImage(User user, MultipartFile file) {
	        	 Optional<User> optionalUser = userRepository.findById(user.getId());

	             if (optionalUser.isPresent()) {
	                 User existingUser = optionalUser.get();

	                 try {
	                     // Create the uploads directory if it doesn't exist
	                     File uploadDir = new File(UPLOAD_DIR);
	                     if (!uploadDir.exists()) {
	                         uploadDir.mkdirs();
	                     }
	                     // Generate a unique file name
	                     String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	                     Path filePath = Paths.get(UPLOAD_DIR + fileName);
	                     // Save the image file to the file system
	                     Files.write(filePath, file.getBytes());
	                     // Set the file path in the existing user object
	                     existingUser.setImage(filePath.toString());
	                     // Update other user fields from the input user object (if necessary)
	                     existingUser.setName(user.getName());
	                     existingUser.setEmail(user.getEmail());
	                     // Add other fields you want to update
	                     // Save user with the new or updated image path
	                     userRepository.save(existingUser);
	                     return new ResponseEntity<>(HttpStatus.OK);
	                 } catch (IOException e) {
	                     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	                 }
	             } else {
	                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	             }
	         }
	         public ResponseEntity<String> uploadImage(String path, MultipartFile file) {
	        	    try {
	        	        // Get the original file name
	        	        String name = file.getOriginalFilename();
	        	        // Create full path (use the provided path and the file name)
	        	        String filePath = path + File.separator + name;
	        	        // Create directory if it doesn't exist
	        	        System.out.println(filePath);
	        	        File dir = new File(path);
	        	        if (!dir.exists()) {
	        	            dir.mkdirs(); // This creates both parent directories if they don't exist
	        	        }
	        	        // Copy the file to the target directory
	        	        Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);     
	        	        // Return a success response with the file name
	        	        return ResponseEntity.ok("File uploaded successfully: " + name);      	        
	             	    } catch (IOException e) {
	        	        // Return error response in case of an exception
	        	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	        	                .body("File upload failed: " + e.getMessage());
	        	    }
	        	}

}
