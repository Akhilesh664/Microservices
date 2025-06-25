package com.user.service;

import com.user.service.entities.Rating;
import com.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@MockBean
	private RatingService ratingService;


/*
	@Test
	void createRating() {
		Rating rating = Rating.builder()
				.rating(10)
				.userId("validUserId")
				.hotelId("validHotelId")
				.feedback("This is created using Feign client")
				.build();

		ResponseEntity<Rating> savedRating = ratingService.createRating(rating);
		System.out.println("New rating created: " + savedRating);
	}
*/

	/*@Test
	void createRating() {
		Rating rating = Rating.builder()
				.rating(10)
				.userId("valid-user-id")
				.hotelId("valid-hotel-id")
				.feedback("This is created using Feign client")
				.build();

		Rating savedRating = Rating.builder()
				.ratingId("mock-id")
				.rating(10)
				.userId("valid-user-id")
				.hotelId("valid-hotel-id")
				.feedback("This is created using Feign client")
				.build();

		when(ratingService.createRating(rating)).thenReturn(savedRating);

		Rating response = ratingService.createRating(rating);
		System.out.println("New rating created: " + response);

		verify(ratingService).createRating(rating);
	}*/
}
