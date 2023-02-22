package com.rbc.code.challenge.service;

import com.rbc.code.challenge.entity.DowJonesRecord;
import com.rbc.code.challenge.error.InvalidDateException;
import com.rbc.code.challenge.error.RecordNotFoundException;
import com.rbc.code.challenge.model.DowJonesRecordDTO;
import com.rbc.code.challenge.repository.DowJonesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DowJonesServiceImpl implements DowJonesService {

    @Autowired
    private DowJonesRepository dowJonesRepository;

    public List<DowJonesRecordDTO> getByStock(String stock) throws RecordNotFoundException {
        List<DowJonesRecord> entities =
                dowJonesRepository.findByStock(stock);

        if (entities.isEmpty()) {
            throw new RecordNotFoundException("no record found for stock ticker provided: " + stock);
        }

        List<DowJonesRecordDTO> dtos = new ArrayList<>();
        for (DowJonesRecord entity : entities) {
            DowJonesRecordDTO dto = entity.getDTO();
            dtos.add(dto);
        }
        return dtos;
}

    public void saveAll(List<DowJonesRecordDTO> dtos) {

        List<DowJonesRecord> entities = new ArrayList<>();
        for(DowJonesRecordDTO dto: dtos) {
            // Validate the date field
            LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("M/d/yyyy"));

            DowJonesRecord entity = new DowJonesRecord(dto);
            entities.add(entity);
        }
        dowJonesRepository.saveAll(entities);
    }

    @Override
    public DowJonesRecordDTO addRecord(DowJonesRecordDTO dto) throws InvalidDateException{
        // Validate the date field
        LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("M/d/yyyy"));

        DowJonesRecord entity = new DowJonesRecord(dto);
        dowJonesRepository.save(entity);
        return dto;
    }
}
