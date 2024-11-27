package com.sprintboot.evote.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintboot.evote.model.voter;

import jakarta.transaction.Transactional;

public interface VoterRepository extends JpaRepository<voter,Long>{
Optional<voter> findByAadhar(String aadhar);

Optional<voter> findById(int voter_id);

@Query(value = "SELECT s.state, COUNT(v.id) FROM voter v INNER JOIN state s ON v.state_id = s.id GROUP BY s.state", nativeQuery = true)
List<Object[]> getStateVoterCounts();


}
