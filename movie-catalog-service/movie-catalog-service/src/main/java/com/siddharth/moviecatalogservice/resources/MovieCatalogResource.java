package com.siddharth.moviecatalogservice.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.moviecatalogservice.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")// curly braces say that it is  a variable
	public List<CatalogItem> getCatalog(@PathVariable("userId")  String userID){
		
		//right now we are just sending back hardcoded data.
		return Collections.singletonList(
				new CatalogItem("Transformers","Test",4)
				);		
	}
}
