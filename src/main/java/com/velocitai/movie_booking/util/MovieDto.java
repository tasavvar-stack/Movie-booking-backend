package com.velocitai.movie_booking.util;

import java.time.LocalTime;
import java.util.Set;

import com.velocitai.movie_booking.model.Movie;
import com.velocitai.movie_booking.model.Show;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for Movie")
public class MovieDto {

    @Schema(description = "Unique identifier of the movie", example = "1")
    private long id;

    @Schema(description = "Name of the movie", example = "Inception", required = true)
    private String moviename;

    @Schema(description = "Language of the movie", example = "English", required = true)
    private String movieLanguage;

    @Schema(description = "Duration of the movie in the format HH:mm:ss", example = "02:28:00", required = true)
    private LocalTime duration;

    @Schema(description = "Genre of the movie", example = "Sci-Fi", required = true)
    private String genre;

    @Schema(description = "URL of the movie image", example = "http://example.com/image.jpg")
    private String movieImage;

    @Schema(description = "Set of shows associated with the movie")
    private Set<Show> shows;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.moviename = movie.getMoviename();
        this.movieLanguage = movie.getMovieLanguage();
        this.duration = movie.getDuration();
        this.genre = movie.getGenre();
        this.movieImage = movie.getMovieImage();
    }
}
