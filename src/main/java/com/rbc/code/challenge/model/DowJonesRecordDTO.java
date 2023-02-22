package com.rbc.code.challenge.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DowJonesRecordDTO {

    @CsvBindByPosition(position = 0)
    private String quarter;

    @CsvBindByPosition(position = 1)
    private String stock;

    @CsvBindByPosition(position = 2)
    private String date;

    @CsvBindByPosition(position = 3)
    private String open;

    @CsvBindByPosition(position = 4)
    private String high;

    @CsvBindByPosition(position = 5)
    private String low;

    @CsvBindByPosition(position = 6)
    private String close;

    @CsvBindByPosition(position = 7)
    private String volume;

    @CsvBindByPosition(position = 8)
    private String percentChangePrice;

    @CsvBindByPosition(position = 9)
    private String percentChangeVolumeOverLastWeek;

    @CsvBindByPosition(position = 10)
    private String previousWeeksVolume;

    @CsvBindByPosition(position = 11)
    private String nextWeeksOpen;

    @CsvBindByPosition(position = 12)
    private String nextWeeksClose;

    @CsvBindByPosition(position = 13)
    private String percentChangeNextWeeksPrice;

    @CsvBindByPosition(position = 14)
    private int daysToNextDividend;

    @CsvBindByPosition(position = 15)
    private String percentReturnNextDividend;

    public Date convertDateAsDate() {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return format.parse(this.date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + this.date);
        }
    }
}
