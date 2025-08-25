package com.velocitai.movie_booking.model;


import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.velocitai.movie_booking.util.City;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "theaters")
@Component
@Getter
@Setter
public class Theater {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "theater_id")
	private long id;
	@Column(name = "theater_name")
	private String name;
	@Column(name = "theater_address")
	private String address;
	@Enumerated(EnumType.STRING)
	private City city;
	@JsonIgnore
	@OneToMany(mappedBy = "theater") // Indicates the 'theater' field in the Show class
	private List<Show> showTimes;
	@JsonIgnore
	@OneToMany
	private List<Movie> movies;
    
	
}
