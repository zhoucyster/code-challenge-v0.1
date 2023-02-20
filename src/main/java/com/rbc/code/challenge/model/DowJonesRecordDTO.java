package com.rbc.code.challenge.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


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
    private BigDecimal open;
    @CsvBindByPosition(position = 4)
    private BigDecimal high;
    @CsvBindByPosition(position = 5)
    private BigDecimal low;
    @CsvBindByPosition(position = 6)
    private BigDecimal close;
    @CsvBindByPosition(position = 7)
    private BigDecimal volume;
    @CsvBindByPosition(position = 8)
    private BigDecimal percentChangePrice;
    @CsvBindByPosition(position = 9)
    private BigDecimal percentChangeVolumeOverLastWeek;
    @CsvBindByPosition(position = 10)
    private BigDecimal previousWeeksVolume;
    @CsvBindByPosition(position = 11)
    private BigDecimal nextWeeksOpen;
    @CsvBindByPosition(position = 12)
    private BigDecimal nextWeeksClose;
    @CsvBindByPosition(position = 13)
    private BigDecimal percentChangeNextWeeksPrice;
    @CsvBindByPosition(position = 14)
    private int daysToNextDividend;
    @CsvBindByPosition(position = 15)
    private BigDecimal percentReturnNextDividend;

    public Date convertDateAsDate() {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return format.parse(this.date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + this.date);
        }
    }
}
