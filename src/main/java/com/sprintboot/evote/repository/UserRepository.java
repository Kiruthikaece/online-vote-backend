package com.sprintboot.evote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintboot.evote.model.user;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<user,Long> {
	Optional<user> findByAadhar(String aadhar);
	
	@Modifying
	@Transactional
	@Query("UPDATE user u SET u.fname = :fname, u.lname = :lname, u.aadhar = :aadhar, u.phoneno = :phoneNo WHERE u.id = :id")
	int updateUser(@Param("id") Long id, 
	               @Param("fname") String fname, 
	               @Param("lname") String lname, 
	               @Param("aadhar") String aadhar, 
	               @Param("phoneNo") String phoneNo);

}
