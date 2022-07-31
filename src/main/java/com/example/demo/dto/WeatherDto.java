package com.example.demo.dto;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;

import com.example.demo.model.Temperature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class WeatherDto {
	 
	@JsonFormat(pattern="yyyy-MM-dd",shape=Shape.STRING)
	 private String date;
		
//	 private List<Temperature> temperature;
	 
	 
	 @ElementCollection(targetClass=Float.class)
	 private List<Float> temperature;
	 
	 private int location;

	

//	public List<Temperature> getTemperature() {
//		return temperature;
//	}
//
//	public void setTemperature(List<Temperature> temperature) {
//		this.temperature = temperature;
//	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public List<Float> getTemperature() {
		return temperature;
	}

	public void setTemperature(List<Float> temperature) {
		this.temperature = temperature;
	}
	
	

	
	 
	 
		

}
