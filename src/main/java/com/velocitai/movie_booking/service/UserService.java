package com.velocitai.movie_booking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velocitai.movie_booking.model.User;

import jakarta.mail.Multipart;

@Service
public interface UserService {

	    public List<User> allUsers();
		public ResponseEntity<User> findUserById(long id);
		public ResponseEntity<User> UpdateUser(User user);
		public ResponseEntity<User> deleteUser(User user);
		public ResponseEntity<User> saveUserImage(User user,MultipartFile file);
		public ResponseEntity<?> getUserImage();
		public ResponseEntity<String> uploadImage(String path, MultipartFile image);
}
