package com.sprintboot.evote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "constituents")
public class Constituent {

	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private int id;
	  private int stateId;
	  private int districtId;
	  private String constituent;
	  
	  public Constituent(int id) {
		    this.id = id;
		}
	  
	  public Constituent() {
		}
	  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getConstituentName() {
		return constituent;
	}
	public void setConstituentName(String constituent) {
		this.constituent = constituent;
	}
}
