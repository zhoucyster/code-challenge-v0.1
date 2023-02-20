package com.rbc.code.challenge.service;

import com.rbc.code.challenge.entity.DowJonesRecord;
import com.rbc.code.challenge.error.InvalidDateException;
import com.rbc.code.challenge.error.RecordNotFoundException;
import com.rbc.code.challenge.model.DowJonesRecordDTO;
import com.rbc.code.challenge.repository.DowJonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            DowJonesRecordDTO dto = new DowJonesRecordDTO();
            dto.setQuarter(entity.getQuarter());
            dto.setStock(entity.getStock());
            dto.setDate(new SimpleDateFormat("M/d/yyyy").format(entity.getDate()));
            dto.setOpen(entity.getOpen());
            dto.setHigh(entity.getHigh());
            dto.setLow(entity.getLow());
            dto.setClose(entity.getClose());
            dto.setVolume(entity.getVolume());
            dto.setPercentChangePrice(entity.getPercentChangePrice());
            dto.setPercentChangeVolumeOverLastWeek(entity.getPercentChangeVolumeOverLastWeek());
            dto.setPreviousWeeksVolume(entity.getPreviousWeeksVolume());
            dto.setNextWeeksOpen(entity.getNextWeeksOpen());
            dto.setNextWeeksClose(entity.getNextWeeksClose());
            dto.setPercentChangeNextWeeksPrice(entity.getPercentChangeNextWeeksPrice());
            dto.setDaysToNextDividend(entity.getDaysToNextDividend());
            dto.setPercentReturnNextDividend(entity.getPercentReturnNextDividend());
            dtos.add(dto);
        }
        return dtos;
}

    public void saveAll(List<DowJonesRecordDTO> dtos) {

        List<DowJonesRecord> entities = new ArrayList<>();
        for(DowJonesRecordDTO dto: dtos) {
            DowJonesRecord entity = new DowJonesRecord();
            entity.setQuarter(dto.getQuarter());
            entity.setStock(dto.getStock());
            entity.setDate(dto.convertDateAsDate());
            entity.setOpen(dto.getOpen());
            entity.setHigh(dto.getHigh());
            entity.setLow(dto.getLow());
            entity.setClose(dto.getClose());
            entity.setVolume(dto.getVolume());
            entity.setPercentChangePrice(dto.getPercentChangePrice());
            entity.setPercentChangeVolumeOverLastWeek(dto.getPercentChangeVolumeOverLastWeek());
            entity.setPreviousWeeksVolume(dto.getPreviousWeeksVolume());
            entity.setNextWeeksOpen(dto.getNextWeeksOpen());
            entity.setNextWeeksClose(dto.getNextWeeksClose());
            entity.setPercentChangeNextWeeksPrice(dto.getPercentChangeNextWeeksPrice());
            entity.setDaysToNextDividend(dto.getDaysToNextDividend());
            entity.setPercentReturnNextDividend(dto.getPercentReturnNextDividend());
            entities.add(entity);
        }
        dowJonesRepository.saveAll(entities);
    }

    @Override
    public DowJonesRecordDTO addRecord(DowJonesRecordDTO dto) throws InvalidDateException{
        // Validate the date field
        try {
            LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("M/d/yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date format: " + dto.getDate());
        }
        DowJonesRecord entity = new DowJonesRecord();
        entity.setQuarter(dto.getQuarter());
        entity.setStock(dto.getStock());
        entity.setDate(dto.convertDateAsDate());
        entity.setOpen(dto.getOpen());
        entity.setHigh(dto.getHigh());
        entity.setLow(dto.getLow());
        entity.setClose(dto.getClose());
        entity.setVolume(dto.getVolume());
        entity.setPercentChangePrice(dto.getPercentChangePrice());
        entity.setPercentChangeVolumeOverLastWeek(dto.getPercentChangeVolumeOverLastWeek());
        entity.setPreviousWeeksVolume(dto.getPreviousWeeksVolume());
        entity.setNextWeeksOpen(dto.getNextWeeksOpen());
        entity.setNextWeeksClose(dto.getNextWeeksClose());
        entity.setPercentChangeNextWeeksPrice(dto.getPercentChangeNextWeeksPrice());
        entity.setDaysToNextDividend(dto.getDaysToNextDividend());
        entity.setPercentReturnNextDividend(dto.getPercentReturnNextDividend());
        dowJonesRepository.save(entity);
        return dto;
    }
}
