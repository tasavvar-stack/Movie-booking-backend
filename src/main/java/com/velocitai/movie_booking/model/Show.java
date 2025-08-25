package com.velocitai.movie_booking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shows")
@Component
@Getter
@Setter

public class Show {

	 @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "show_id")
		private long id;
		@Column(name = "ShowTimming")
		private LocalTime time;
		@Column(name = "DateOfShow")
		private LocalDate date;
		
		@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
		private List<Seat> seat;
		@JsonIgnore
		@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
		private Theater theater;
		@JsonIgnore
		@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private Movie movie;
	
		
}
