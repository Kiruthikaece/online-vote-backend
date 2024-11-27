package com.sprintboot.evote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintboot.evote.model.Constituent;

public interface ConstituentRepository extends JpaRepository<Constituent,Long>{
	public List<Constituent> findByDistrictId(int districtId); 
	public Optional<Constituent> findById(int id);

}
