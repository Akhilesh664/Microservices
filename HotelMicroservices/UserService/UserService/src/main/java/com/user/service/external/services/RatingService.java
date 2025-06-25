package com.user.service.external.services;

import com.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //GET

    //POST
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(@RequestBody Rating values);

    //PUT
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable("ratingId")  String ratingId , @RequestBody Rating values);

    //DELETE
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);
}
