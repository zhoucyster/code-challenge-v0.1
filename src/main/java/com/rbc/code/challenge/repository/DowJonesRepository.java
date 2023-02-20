package com.rbc.code.challenge.repository;

import com.rbc.code.challenge.entity.DowJonesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DowJonesRepository extends JpaRepository<DowJonesRecord, Long> {

    List<DowJonesRecord> findByStock(String stock);
}

