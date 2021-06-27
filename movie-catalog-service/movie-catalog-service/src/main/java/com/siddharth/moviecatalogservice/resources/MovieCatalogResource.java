package com.siddharth.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.siddharth.moviecatalogservice.models.CatalogItem;
import com.siddharth.moviecatalogservice.models.Movie;
import com.siddharth.moviecatalogservice.models.Rating;
import com.siddharth.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")// curly braces say that it is  a variable
	public List<CatalogItem> getCatalog(@PathVariable("userId")  String userID){
		
		//get UserRating object which contains a list of user rating as properties
		UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+ userID, UserRating.class);
			
		//we got to fetch the list from the UserRating object hence ratings.getUserRatings
		return ratings.getUserRating().stream().map(x -> {
			
			//for each movie id call movie info service and get details
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+x.getMovieId(), Movie.class);
			
			//put them all together
			return new CatalogItem(movie.getName(), "Test",x.getRating());
		})
		.collect(Collectors.toList());
	
	
	}
}
