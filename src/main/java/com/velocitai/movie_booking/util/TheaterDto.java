package com.velocitai.movie_booking.util;

import java.util.List;

import com.velocitai.movie_booking.model.Theater;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterDto {

	private long id;
	private String name;
	private String address;
	private List<MovieDto> movies;
	public TheaterDto(Theater theater) {
		
		this.id = theater.getId();
		this.name = theater.getName();
		this.address = theater.getAddress();
		
	}
	
	
	
}
