package com.sprintboot.evote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintboot.evote.model.state;

public interface StateRepository extends JpaRepository<state,Long> {

}
