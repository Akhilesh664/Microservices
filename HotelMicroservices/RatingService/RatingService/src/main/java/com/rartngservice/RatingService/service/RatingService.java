package com.rartngservice.RatingService.service;

import com.rartngservice.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    //create rating
    Rating create (Rating rating);

    //get all rating
    List<Rating> getRating();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);


}
