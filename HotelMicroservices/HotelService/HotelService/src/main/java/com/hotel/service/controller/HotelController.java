package com.hotel.service.controller;

import com.hotel.service.entites.Hotel;
import com.hotel.service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //create api

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }


   // get single api
   @GetMapping("/{hotelId}")
   public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId){
       return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
   }


    //get all
    @GetMapping
    public ResponseEntity<List <Hotel>> getAll(){
        return ResponseEntity.ok(hotelService.getAll());
    }

    // Delete hotel by ID
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelId) {
        hotelService.delete(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body("Hotel deleted successfully!");
    }

    //update hotel
    @PutMapping("/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelId, @RequestBody Hotel updatedHotel) {
        Hotel hotel = hotelService.update(hotelId, updatedHotel);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }


}
