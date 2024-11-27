package com.sprintboot.evote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintboot.evote.model.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByStateId(Long stateId); 
}
