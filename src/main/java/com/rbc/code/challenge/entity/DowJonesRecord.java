package com.rbc.code.challenge.entity;

import com.rbc.code.challenge.model.DowJonesRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DowJonesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quarter;
    private String stock;
    private Date date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;

    @Column(precision = 19, scale = 6)
    private BigDecimal percentChangePrice;

    @Column(precision = 19, scale = 6)
    private BigDecimal percentChangeVolumeOverLastWeek;

    private BigDecimal previousWeeksVolume;
    private BigDecimal nextWeeksOpen;
    private BigDecimal nextWeeksClose;

    @Column(precision = 19, scale = 6)
    private BigDecimal percentChangeNextWeeksPrice;

    private int daysToNextDividend;

    @Column(precision = 19, scale = 6)
    private BigDecimal percentReturnNextDividend;

    private BigDecimal stringToBigDecimalConverter(String s) {
        int scale = 6;
        if (s == null || s.trim().length() == 0) {
            return null;
        }
        if (s.startsWith("$")) {
            s = s.substring(1);
        }
        if (s.equals("")) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(s).setScale(scale, RoundingMode.HALF_UP);
    }

    private String bigDecimalToStringConverter(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal.toString() : "";
    }

    public DowJonesRecord(DowJonesRecordDTO dto) {
        this.quarter = dto.getQuarter();
        this.stock = dto.getStock();
        this.date = dto.convertDateAsDate();
        this.open = stringToBigDecimalConverter(dto.getOpen());
        this.high = stringToBigDecimalConverter(dto.getHigh());
        this.low = stringToBigDecimalConverter(dto.getLow());
        this.close = stringToBigDecimalConverter(dto.getClose());
        this.volume = stringToBigDecimalConverter(dto.getVolume());
        this.percentChangePrice = stringToBigDecimalConverter(dto.getPercentChangePrice());
        this.percentChangeVolumeOverLastWeek = stringToBigDecimalConverter(dto.getPercentChangeVolumeOverLastWeek());
        this.previousWeeksVolume = stringToBigDecimalConverter(dto.getPreviousWeeksVolume());
        this.nextWeeksOpen = stringToBigDecimalConverter(dto.getNextWeeksOpen());
        this.nextWeeksClose = stringToBigDecimalConverter(dto.getNextWeeksClose());
        this.percentChangeNextWeeksPrice = stringToBigDecimalConverter(dto.getPercentChangeNextWeeksPrice());
        this.daysToNextDividend = dto.getDaysToNextDividend();
        this.percentReturnNextDividend = stringToBigDecimalConverter(dto.getPercentReturnNextDividend());
    }

    public DowJonesRecordDTO getDTO() {
        DowJonesRecordDTO dto = new DowJonesRecordDTO();
        dto.setQuarter(this.quarter);
        dto.setStock(this.stock);
        dto.setDate(new SimpleDateFormat("M/d/yyyy").format(this.date));
        dto.setOpen(bigDecimalToStringConverter(this.open));
        dto.setHigh(bigDecimalToStringConverter(this.high));
        dto.setLow(bigDecimalToStringConverter(this.low));
        dto.setClose(bigDecimalToStringConverter(this.close));
        dto.setVolume(bigDecimalToStringConverter(this.volume));
        dto.setPercentChangePrice(bigDecimalToStringConverter(this.percentChangePrice));
        dto.setPercentChangeVolumeOverLastWeek(bigDecimalToStringConverter(this.percentChangeVolumeOverLastWeek));
        dto.setPreviousWeeksVolume(bigDecimalToStringConverter(this.previousWeeksVolume));
        dto.setNextWeeksOpen(bigDecimalToStringConverter(this.nextWeeksOpen));
        dto.setNextWeeksClose(bigDecimalToStringConverter(this.nextWeeksClose));
        dto.setPercentChangeNextWeeksPrice(bigDecimalToStringConverter(this.percentChangeNextWeeksPrice));
        dto.setDaysToNextDividend(this.daysToNextDividend);
        dto.setPercentReturnNextDividend(bigDecimalToStringConverter(this.percentReturnNextDividend));
        return dto;
    }
}

