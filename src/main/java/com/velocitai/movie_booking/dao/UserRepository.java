package com.velocitai.movie_booking.dao;

import java.awt.Image;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.velocitai.movie_booking.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String username);

}
