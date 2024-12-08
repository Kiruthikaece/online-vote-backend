package com.sprintboot.evote.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class voter {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	    private LocalDate dob;
	    private String gender;
	    private int party_id;
	    private boolean isvote;
	    private int voter_id;
	    private String aadhar;
	    

	    
	    
	    @ManyToOne
	    @JoinColumn(name = "state_id", referencedColumnName = "id")
	    private state states;

	    @ManyToOne
	    @JoinColumn(name = "district_id", referencedColumnName = "id")
	    private District district;

	    @ManyToOne
	    @JoinColumn(name = "constituent_id", referencedColumnName = "id")
	    private Constituent constituent;
	    
		public Constituent getConstituent() {
			return constituent;
		}
		public void setConstituent(Constituent constituent) {
			this.constituent = constituent;
		}
		public District getDistrict() {
			return district;
		}
		public void setDistrict(District district) {
			this.district = district;
		}
		public state getStates() {
			return states;
		}
		public void setStates(state states) {
			this.states = states;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
			 public void setDob(LocalDate dob) {
			        this.dob = dob;
			    }

			    public LocalDate getDob() {
			        return dob;
			    }
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public int getPartyId() {
			return party_id;
		}
		public void setPartyId(int partyId) {
			this.party_id = partyId;
		}
		public boolean isIsvote() {
			return isvote;
		}
		public void setIsvote(boolean isvote) {
			this.isvote = isvote;
		}
		public int getVoterId() {
			return voter_id;
		}
		public void setVoterId(int voterId) {
			this.voter_id = voterId;
		}
		public String getAadhar() {
			return aadhar;
		}
		public void setAadhar(String aadhar) {
			this.aadhar = aadhar;
		}
		
		

	
}
