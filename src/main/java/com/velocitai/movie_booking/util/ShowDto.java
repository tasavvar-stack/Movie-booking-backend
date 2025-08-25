package com.velocitai.movie_booking.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.velocitai.movie_booking.model.Seat;
import com.velocitai.movie_booking.model.Show;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for Show")
public class ShowDto {

    @Schema(description = "Unique identifier of the show", example = "1")
    private Long id;

    @Schema(description = "Time of the show", example = "14:30:00", required = true)
    private LocalTime time;

    @Schema(description = "Date of the show", example = "2024-10-13", required = true)
    private LocalDate date;

    @Schema(description = "List of seats associated with the show")
    private List<Seat> seats;

    public ShowDto(Show show) {
        this.id = show.getId();
        this.time = show.getTime();
        this.date = show.getDate();
        this.seats = show.getSeat();
    }
}
