package com.sprintboot.evote.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sprintboot.evote.model.Constituent;
import com.sprintboot.evote.model.District;
import com.sprintboot.evote.model.Party;
import com.sprintboot.evote.model.state;
import com.sprintboot.evote.model.user;
import com.sprintboot.evote.model.voter;
import com.sprintboot.evote.repository.ConstituentRepository;
import com.sprintboot.evote.repository.DistrictRepository;
import com.sprintboot.evote.repository.PartyRepository;
import com.sprintboot.evote.repository.StateRepository;
import com.sprintboot.evote.repository.UserRepository;
import com.sprintboot.evote.repository.VoterRepository;

@RestController
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VoterRepository voterRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	ConstituentRepository constituentRepository;
	
	@Autowired
	DistrictRepository districtRepository;
	
	@Autowired
	PartyRepository partyRepository;
	
	@PostMapping("/api/checkUser")
	 public Map<String,Object> check(@RequestBody Map<String, String> request) {
        
		String aadharNo = request.get("aadharNo");
        System.out.println(aadharNo+"aadharNo");
        
        Optional<user> isUser = userRepository.findByAadhar(aadharNo);
        Optional<voter> isVoter= voterRepository.findByAadhar(aadharNo);
        
        Map<String,Object> loginUser=new HashMap<>();
        
        if(isUser.isPresent() && isVoter.isPresent()) {
        	user login=isUser.get();
        	voter vote=isVoter.get();
        	
        	 loginUser.put("id", login.getId());
             loginUser.put("fname", login.getFname());
             loginUser.put("lname", login.getLname());
             loginUser.put("aadhar", login.getAadhar());
             loginUser.put("phoneno", login.getPhoneno());

             // Add voter fields
             loginUser.put("dob", vote.getDob());
             loginUser.put("gender", vote.getGender());
             loginUser.put("partyId", vote.getPartyId());
             loginUser.put("isVote", vote.isIsvote());
             loginUser.put("voterId", vote.getVoterId());
             loginUser.put("state", vote.getStates().getName());
             loginUser.put("District", vote.getDistrict().getDistrictName());
             loginUser.put("constituent", vote.getConstituent().getConstituentName());

             loginUser.put("status", "success");
        } else {
        	loginUser.put("status", "failed");
        	loginUser.put("message", "Aadhar number not found.");
        }
        
        return loginUser;
    }
	
	@GetMapping("/api/states")
	public List<state> getAllStates() {
		return stateRepository.findAll();
	}
	
	@GetMapping("/api/district/{stateId}")
	public List<District> getAllDistrict(@PathVariable("stateId") Long stateId) {
	    return districtRepository.findByStateId(stateId);
	}


	
	@GetMapping("/api/constituent/{districtId}")
	public List<Constituent> getAllConstituent(@PathVariable int districtId) {
	    return constituentRepository.findByDistrictId(districtId);
	}
	
	@GetMapping("/api/party")
	public List<Party> getAllParty() {
		return partyRepository.findAll();
	}
	
	@PutMapping("/api/submit-vote")
	public ResponseEntity<Map<String, String>> updateVote(@RequestBody Map<String, Object> request) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        int voterId = Integer.parseInt(request.get("voterId").toString());
	        int partyId = Integer.parseInt(request.get("partyId").toString());
	        boolean isVote = Boolean.parseBoolean(request.get("isVote").toString());

	        Optional<voter> optionalVoter = voterRepository.findById(voterId);
	        if (optionalVoter.isPresent()) {
	            voter voterToUpdate = optionalVoter.get();
	            voterToUpdate.setPartyId(partyId);
	            voterToUpdate.setIsvote(isVote);

	            voterRepository.save(voterToUpdate);
	            response.put("message", "Vote updated successfully.");
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("error", "Voter not found.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (NumberFormatException e) {
	        response.put("error", "Invalid voterId or partyId format.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (Exception e) {
	        response.put("error", "An error occurred while updating the vote.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	

	@PutMapping("/api/update-voter")
	public ResponseEntity<?> updateVoter(@RequestBody Map<String, Object> formData) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        System.out.println("Received formData: " + formData);

	        // Validate required fields
	        String[] requiredKeys = {"id", "voterId", "state_id", "district_id", "constituent_id"};
	        for (String key : requiredKeys) {
	            if (formData.get(key) == null) {
	                throw new RuntimeException("Missing required field: " + key);
	            }
	        }

	        // Parse user ID
	        Long userId = Long.parseLong(formData.get("id").toString());
	        user existingUser = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // Update user details
	        existingUser.setFname((String) formData.get("fname"));
	        existingUser.setLname((String) formData.get("lname"));
	        existingUser.setAadhar((String) formData.get("aadhar"));
	        existingUser.setPhoneno((String) formData.get("phoneNo"));
	        userRepository.save(existingUser);

	        // Parse voter ID
	        Long voterId = Long.parseLong(formData.get("voterId").toString());
	        voter existingVoter = voterRepository.findById(voterId)
	                .orElseThrow(() -> new RuntimeException("Voter not found"));

	        // Parse and set DOB
	        String dobString = (String) formData.get("dob");
	        LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ISO_DATE);
	        existingVoter.setDob(dob);

	        // Update gender
	        existingVoter.setGender((String) formData.get("gender"));
	        existingVoter.setAadhar((String) formData.get("aadhar"));
	        
	        int stateId = Integer.parseInt(formData.get("state_id").toString());
	        int districtId = Integer.parseInt(formData.get("district_id").toString());
	        int constituentId = Integer.parseInt(formData.get("constituent_id").toString());

	        existingVoter.setStates(new state(stateId));
	        existingVoter.setDistrict(new District(districtId));
	        existingVoter.setConstituent(new Constituent(constituentId));

	        // Associate voter with user
	        existingVoter.setVoterId(existingUser.getId().intValue());
	        voterRepository.save(existingVoter);

	        // Construct a success response
	        response.put("message", "Voter details updated successfully!");
	        response.put("status", "success");
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        // Log and return error response
	        System.out.println("Error occurred while processing formData: " + formData);
	        e.printStackTrace();

	        response.put("message", "Error: " + e.getMessage());
	        response.put("status", "error");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	
	
	@PostMapping("/api/add-voter")
	public ResponseEntity<Map<String, Object>> registerVoter(@RequestBody Map<String, Object> formData) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	
	        String fname = (String) formData.get("fname");
	        String lname = (String) formData.get("lname");
	        String aadhar = (String) formData.get("aadhar");
	        String phoneNo = (String) formData.get("phoneNo");

			Optional<user> existingUser = userRepository.findByAadhar(aadhar);
			if (existingUser.isPresent()) {
				response.put("message", "User with this Aadhar number already exists.");
				response.put("status", "error");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
	
	        user newUser = new user();
	        newUser.setFname(fname);
	        newUser.setLname(lname);
	        newUser.setAadhar(aadhar);
	        newUser.setPhoneno(phoneNo);
	        

	        String dobString = (String) formData.get("dob");
	        LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ISO_DATE);
	        String gender = (String) formData.get("gender");
	        int stateId = Integer.parseInt((String) formData.get("state_id"));
	        int districtId = Integer.parseInt((String) formData.get("district_id"));
	        int constituentId = Integer.parseInt((String) formData.get("constituent_id"));

			LocalDate today = LocalDate.now();
            Period age = Period.between(dob, today);
             if (age.getYears() < 18) {
            response.put("message", "User must be 18 years or older.");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

	     	newUser = userRepository.save(newUser);
	        voter newVoter = new voter();
	        newVoter.setDob(dob);
	        newVoter.setGender(gender);
	        newVoter.setStates(new state(stateId));
	        newVoter.setDistrict(new District(districtId));
	        newVoter.setConstituent(new Constituent(constituentId));
	        newVoter.setAadhar(aadhar);
	        newVoter.setVoterId(newUser.getId().intValue());
	        voterRepository.save(newVoter);

	        // Construct a JSON response
	        response.put("message", "Voter registered successfully!");
	        response.put("status", "success");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("message", "Error: " + e.getMessage());
	        response.put("status", "error");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@GetMapping("/api/state-voter-count")
    public List<Object[]> getStateVoterCount() {
        return voterRepository.getStateVoterCounts();
    }
}



