package com.velocitai.movie_booking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.velocitai.movie_booking.model.Theater;
import com.velocitai.movie_booking.util.City;
@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long>{
	
	List<Theater> findByAddressContainingIgnoreCase(String location);

	List<Theater> findByAddress(String address);
	

	 @Query("SELECT t FROM Theater t WHERE LOWER(t.address) LIKE LOWER(CONCAT('%', :address, '%'))")
	    List<Theater> searchByAddress(@Param("address") String address);

	 // Method to find a theater by name (if needed)
	    Optional<Theater> findByName(String name);


//	@Query("SELECT t FROM Theater t WHERE LOWER(t.address) LIKE LOWER(CONCAT('%', :address, '%'))")
//    List<Theater> searchByAddress(@Param("address") String address);
    
//    @Query("SELECT t FROM Theater t JOIN t.showTime s WHERE s.movie.moviename = :movieName")
	@Query("SELECT t FROM Theater t JOIN FETCH t.showTimes s JOIN FETCH s.movie m WHERE LOWER(m.moviename) LIKE LOWER(CONCAT('%', :moviename, '%'))")
    List<Theater> findByShow_MovieName( String moviename);

	List<Theater> findByCity(City city);


}
