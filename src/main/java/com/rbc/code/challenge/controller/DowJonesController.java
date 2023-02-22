package com.rbc.code.challenge.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import com.rbc.code.challenge.error.InvalidDateException;
import com.rbc.code.challenge.error.RecordNotFoundException;
import com.rbc.code.challenge.model.DowJonesRecordDTO;
import com.rbc.code.challenge.service.DowJonesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@RestController
@RequestMapping("/api/dowjones")
public class DowJonesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DowJonesController.class);

    @Autowired
    private ReadWriteLock lock;

    @Autowired
    private DowJonesService dowJonesService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody List<@Valid DowJonesRecordDTO> dtos) {
        lock.writeLock().lock();
        try {
            dowJonesService.saveAll(dtos);
            LOGGER.info("Uploaded {} records", dtos.size());
        } finally {
            lock.writeLock().unlock();
        }

        return new ResponseEntity<>("upload successfully, " + dtos.size() + " records were uploaded.", HttpStatus.CREATED);
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
            throws InvalidDateException, IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file");
        }

        lock.writeLock().lock();
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<DowJonesRecordDTO> dtos = new CsvToBeanBuilder<DowJonesRecordDTO>(reader)
                    .withType(DowJonesRecordDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .withSkipLines(1)
                    .build()
                    .parse();
            for (DowJonesRecordDTO dto : dtos) {
                dowJonesService.addRecord(dto);
            }
            LOGGER.info("Uploaded {} records", dtos.size());
            return ResponseEntity.ok("File uploaded successfully, " + dtos.size() + " records were uploaded.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    @GetMapping("/query")
    public ResponseEntity<List<DowJonesRecordDTO>> query(@RequestParam("stock") String stock)
            throws RecordNotFoundException{
        lock.readLock().lock();
        try {
            List<DowJonesRecordDTO> records = dowJonesService.getByStock(stock);
            LOGGER.info("Queried for stock: {}, found {} records", stock, records.size());
            return new ResponseEntity<>(records, HttpStatus.OK);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Valid DowJonesRecordDTO dto)
            throws InvalidDateException {
        lock.writeLock().lock();
        try {
            dowJonesService.addRecord(dto);
            LOGGER.info("Added record: {}", dto);
            return new ResponseEntity<>("record was added successfully", HttpStatus.CREATED);
        } finally {
            lock.writeLock().unlock();
        }
    }
}

