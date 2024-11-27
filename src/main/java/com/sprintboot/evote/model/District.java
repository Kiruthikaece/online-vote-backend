package com.sprintboot.evote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private int stateId; // Changed from state_id to stateId in camel case
    
    private String district;
    
    public District() {
	}
    public District(int id) {
	    this.id = id;
	}

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStateId() { // Updated getter
        return stateId;
    }

    public void setStateId(int stateId) { // Updated setter
        this.stateId = stateId;
    }

    public String getDistrictName() {
        return district;
    }

    public void setDistrictName(String district) {
        this.district = district;
    }
}
