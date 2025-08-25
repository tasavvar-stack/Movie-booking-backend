package com.velocitai.movie_booking.model;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Component
@Getter
@Setter
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private long id;
	private String moviename;
	private String movieLanguage;
	private LocalTime duration;
	private String genre;
	@Column(name = "movie_image")
	private String movieImage;

	@JsonIgnore
	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY) // Indicates the 'movie' field in the Show class
	private List<Show> shows;

}
