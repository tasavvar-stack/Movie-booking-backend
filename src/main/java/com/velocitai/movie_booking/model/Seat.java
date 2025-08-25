package com.velocitai.movie_booking.model;

import org.springframework.stereotype.Component;

import com.velocitai.movie_booking.util.SeatType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Component
@Getter
@Setter
public class Seat {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seat_gen")
	@SequenceGenerator(name = "seat_gen",initialValue = 1000,allocationSize = 1)
	@Column(name = "seat_id")
	private Long id;

	@Column(name = "seat_no")
	private String seatNumber;
	@Column(name = "seat_price")
	private int price;
	private boolean booked;
	@Enumerated(EnumType.STRING)
	private SeatType type;
	@ManyToOne(cascade =  CascadeType.ALL)
	private Show show;
	
}
