package com.rbc.code.challenge.service;

import com.rbc.code.challenge.entity.DowJonesRecord;
import com.rbc.code.challenge.error.InvalidDateException;
import com.rbc.code.challenge.error.RecordNotFoundException;
import com.rbc.code.challenge.model.DowJonesRecordDTO;

import java.util.List;

public interface DowJonesService {

    List<DowJonesRecordDTO> getByStock(String stock) throws RecordNotFoundException;

    void saveAll(List<DowJonesRecordDTO> records);

    DowJonesRecordDTO addRecord(DowJonesRecordDTO record) throws InvalidDateException;
}
