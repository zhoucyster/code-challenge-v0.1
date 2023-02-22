package com.rbc.code.challenge.repository;

import com.rbc.code.challenge.entity.DowJonesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DowJonesRepository extends JpaRepository<DowJonesRecord, Long> {

    List<DowJonesRecord> findByStock(String stock);
}

