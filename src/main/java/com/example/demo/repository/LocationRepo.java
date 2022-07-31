package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Location;

public interface LocationRepo extends JpaRepository<Location,Integer>{
    
	@Query(value="SELECT * FROM location u WHERE u.latitude like ?1 and u.longitude like ?2",nativeQuery=true)
	Location findByLati(float latitude,float longitude);

}
