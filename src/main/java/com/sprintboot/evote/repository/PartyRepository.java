package com.sprintboot.evote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintboot.evote.model.Party;

public interface PartyRepository extends JpaRepository<Party,Long>{

	
	
}
