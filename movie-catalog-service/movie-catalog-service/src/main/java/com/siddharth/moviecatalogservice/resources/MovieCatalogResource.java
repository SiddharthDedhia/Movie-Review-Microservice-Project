package com.siddharth.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.siddharth.moviecatalogservice.models.CatalogItem;
import com.siddharth.moviecatalogservice.models.Movie;
import com.siddharth.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")// curly braces say that it is  a variable
	public List<CatalogItem> getCatalog(@PathVariable("userId")  String userID){
		//get all rated movie IDs- hard coded for now
		List<Rating> ratings = Arrays.asList(
				new Rating("1234",4),
				new Rating("5678",3),
				new Rating("9102",2)
				); 
		
		//for each movie ID , call movie Info Service and get details
		//we have the movies and now we will make REST API call to movie info
		
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Test",rating.getRating());
		})
		.collect(Collectors.toList());
	
	
	}
}
