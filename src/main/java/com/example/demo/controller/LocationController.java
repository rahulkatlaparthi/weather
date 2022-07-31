package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepo;


@RestController
public class LocationController {
	
	@Autowired
	private LocationRepo loc;
	
	
	@PostMapping("/addLocation")
	public Location addLocation(@RequestBody Location location) {
		return loc.save(location);
	}
	
	@GetMapping("/getLocations")
	public List<Location> getLocation(){
		return loc.findAll();
	}

	
}
