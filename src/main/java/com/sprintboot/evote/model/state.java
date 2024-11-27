package com.sprintboot.evote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class state {

	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private int id;
	  private String state;
	  
	  
	  public state() {
	   }
	  
	  public state(int id) {
		    this.id = id;
		}
	  
	  
	  
	  public int getId() {
		return id;
	}
	  
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return state;
	}
	public void setName(String state) {
		this.state = state;
	}
	
}
