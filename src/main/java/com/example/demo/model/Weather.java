package com.example.demo.model;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name="weather")
public class Weather {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int weather_id;
	
	@JsonFormat(pattern="yyyy-MM-dd",shape=Shape.STRING)
    private String date;
	
//	private List<Temperature> temperature;
	@Column(name="temperature")
	@ElementCollection(targetClass=Float.class)
	private List<Float> temperature;
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "location_id")
    
    private Location location;

	public int getWeather_id() {
		return weather_id;
	}

	public void setWeather_id(int weather_id) {
		this.weather_id = weather_id;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Float> getTemperature() {
		return temperature;
	}

	public void setTemperature(List<Float> list) {
		this.temperature = list;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	

	}
