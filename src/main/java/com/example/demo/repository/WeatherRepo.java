package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Weather;

public interface WeatherRepo extends JpaRepository<Weather,Integer>{

	Weather save(Weather we);
    
	@Query(value="SELECT * FROM weather u WHERE u.location_id =?1",nativeQuery=true)
	List<Weather> findByLoc(int location_id);
  
	@Query(value="SELECT * FROM weather u WHERE u.date between ?1 and ?2",nativeQuery=true)
	List<Weather> findByDateRange(Date startdate, Date enddate);
 
	@Query(value="SELECT * FROM weather u WHERE u.date =?1 and u.location_id!=?2",nativeQuery=true)
	List<Weather> findByDateAndLocation(Date date, int location_id);
    
//	@Query(value="SELECT * FROM salesperson u WHERE u.phone_no =?1",nativeQuery=true)
//	List<Weather> findAllBYLatAndLong(Float latitude, Float longitude);

}
