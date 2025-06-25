package com.hotel.service.impl;

import com.hotel.service.entites.Hotel;
import com.hotel.service.exception.ResourceNotFoundException;
import com.hotel.service.repository.HotelRepository;
import com.hotel.service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public Hotel create(Hotel hotel) {
        //create random hotel id
       String hotelId = UUID.randomUUID().toString();
       hotel.setId(hotelId);

        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found!!"));
    }


    //delete
    @Override
    public void delete(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given ID not found!"));

        hotelRepository.delete(hotel);
    }

    //update
    @Override
    public Hotel update(String id, Hotel updatedHotel) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given ID not found!"));

        // Update the fields of the existing hotel with the updated hotel details
        existingHotel.setName(updatedHotel.getName());
        existingHotel.setLocation(updatedHotel.getLocation());
        existingHotel.setAbout(updatedHotel.getAbout());
        // Add other fields as needed

        return hotelRepository.save(existingHotel);
    }
    }

