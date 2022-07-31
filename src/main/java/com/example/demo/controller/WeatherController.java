package com.example.demo.controller;

import java.sql.Date;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeatherDto;
import com.example.demo.model.Location;
import com.example.demo.model.PreferredLocation;
import com.example.demo.model.TempeartureRange;
import com.example.demo.model.Weather;
import com.example.demo.repository.LocationRepo;
import com.example.demo.repository.WeatherRepo;

@RestController
public class WeatherController {
	
	
	@Autowired
	private WeatherRepo weatherrepo;
	
	@Autowired
	private LocationRepo locationrepo;
	
	
	@PostMapping("/addweather")
	public Weather addWeather(@RequestBody WeatherDto weather) {
		
		Location loc=locationrepo.findById(weather.getLocation()).get();
		
		Weather we=new Weather();
		
		we.setLocation(loc);
		we.setDate(weather.getDate());
		we.setTemperature(weather.getTemperature());
		
		return weatherrepo.save(we);
		
		
	}
	
	@GetMapping("/getweather")
	public List<Weather> getWeather(){
		
		return weatherrepo.findAll();
	}
	
	@GetMapping("/weather/{latitude}/{longitude}")
	public List<Weather> getWeatherLong(@PathVariable float latitude, @PathVariable float longitude){
		
		Location loc=locationrepo.findByLati(latitude,longitude);
		
		List<Weather> weat=weatherrepo.findByLoc(loc.getLocation_id());
		
		return weat;
	}
	
	@GetMapping("/weather/temperature/{startdate}/{enddate}")
	public List<TempeartureRange> getTempaerature(@PathVariable Date startdate, @PathVariable Date  enddate) {
		List<Weather> weat=weatherrepo.findByDateRange(startdate,enddate);
	
		
		List<TempeartureRange> tempList=new ArrayList<TempeartureRange>();
		
		
		for(int i=0;i<weat.size();i++) {
			TempeartureRange temp=new TempeartureRange();
			temp.setLatitude(weat.get(i).getLocation().getLatitude());
			temp.setLongitude(weat.get(i).getLocation().getLongitude());
			temp.setCity(weat.get(i).getLocation().getCity());
			temp.setState(weat.get(i).getLocation().getState());
			temp.setLowest(retriveMinTemperature(tempList,Collections.min(weat.get(i).getTemperature()),weat.get(i).getLocation().getLatitude()));
			temp.setHighest(retriveMaxTemperature(tempList,Collections.max(weat.get(i).getTemperature()),weat.get(i).getLocation().getLatitude()));
			tempList.add(temp);
			
		}
		
		Collections.reverse(tempList);
		
		
		
//		Set<TempeartureRange> tempSet =new HashSet<TempeartureRange>();
//		
//		for(TempeartureRange x:tempList) {
//			tempSet.add(x);
//		}
		
//		List<TempeartureRange> tempList1=tempList.stream().distinct().collect(Collectors.toList());
		
//		List<TempeartureRange> unique = tempList.stream()
//                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingFloat(TempeartureRange::getLatitude))),
//                                           ArrayList::new));
		
		
		List<TempeartureRange> unique = tempList.stream()
                .map(WrapperTemperature::new)
                .distinct()
                .map(WrapperTemperature::unwrap)
                .collect(Collectors.toList());
		
		
		
		return unique;
		
		
		
	}
	
	public float retriveMinTemperature( List<TempeartureRange> tempList, float minTemp,float location) {
		
		float min=minTemp;
		
		
		for(int i=0;i<tempList.size();i++) {
			if(location==tempList.get(i).getLatitude()) {
				
				if(min > tempList.get(i).getLowest()) {
				   min=tempList.get(i).getLowest();
				}
			}
		}
		
		return min;
		
		
	}
	
public float retriveMaxTemperature( List<TempeartureRange> tempList, float maxTemp,float location) {
		
		float max=maxTemp;
		
		
		for(int i=0;i<tempList.size();i++) {
			if(location==tempList.get(i).getLatitude()) {
				
				if(max < tempList.get(i).getHighest()) {
				   max=tempList.get(i).getHighest();
				}
			}
		}
		
		return max;
		
		
	}
	
	
	
//	@PutMapping("/updatweather/{id}")
//	public Weather updateWeather(@PathVariable Integer id, @RequestBody WeatherDto weather) {
//		
//		Weather we=weatherrepo.findById(id).get();
//		Location loc=locationrepo.findById(weather.getLocation()).get();
//		
////		if(we!=null){
////			Date dt=weather.getDate();
////			Date tomorrow = new Date(dt.getTime() + (1000 * 60 * 60 * 24));
////			we.setDate( tomorrow);
////			we.setLocation(loc);
////			we.setTemperature(weather.getTemperature());
////			return weatherrepo.save(we);
////		}
////		return null;
//		
//		
//	}
	
	@PatchMapping("/modifyweather/{id}")
	public void patchResource(
	        @PathVariable Integer id, 
	        @RequestBody WeatherDto weather ) {
	        

		
		Weather we=weatherrepo.findById(id).get();

	    boolean needUpdate = false;
	    

	    if (weather.getDate()!=null) {
	    	
	        we.setDate(weather.getDate());
	        needUpdate = true;
	    }

	    if (weather.getTemperature().size()>0) {
	        we.setTemperature(weather.getTemperature());
	        needUpdate = true;
	    }

	    if (weather.getLocation()>0) {
	    	Location loc=locationrepo.findById(weather.getLocation()).get();
	    	we.setLocation(loc);
	        needUpdate = true;
	    }

	    if (needUpdate) {
	    	weatherrepo.save(we);
	    }
	}
	
	@DeleteMapping("/deleteweather/{id}")
	public String deleteWeather(@PathVariable Integer id) {
		weatherrepo.deleteById(id);
		return "Successfully Deeleted";
	}
	
	@GetMapping("/weather/location/{date}/{latitude}/{longitude}")
	public List<PreferredLocation> getByGivenLocation(@PathVariable Date date, @PathVariable float latitude, @PathVariable float longitude){
	
	      Location loc=locationrepo.findByLati(latitude,longitude);
	
          List<Weather> weather=weatherrepo.findByDateAndLocation(date,loc.getLocation_id());
          
          double p=0.01745329;
          double r=6371.0;
          
          for(int i=0;i<weather.size();i++) {
        	  float latp=weather.get(i).getLocation().getLatitude();
        	  float latfinal=(latitude-latp)/2;
        	  float longtp=weather.get(i).getLocation().getLongitude();
        	  float longfi=(longitude-longtp)/2;
        	 double a=Math.pow(Math.sin(p*latfinal), 2); 
        	 double b=Math.pow(Math.sin(p*longfi), 2);
        	 double c=Math.cos(p*latp);
        	 double d=Math.cos(p*latitude);
        	 double e=a+b*c*d;
        	 double f=2*r*Math.asin(Math.sqrt(e));
        	 System.out.println("hello");
        	 
          }
          return null;
          
          
}
}
