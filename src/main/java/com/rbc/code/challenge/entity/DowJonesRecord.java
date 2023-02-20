package com.rbc.code.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal percentChangePrice;
    private BigDecimal percentChangeVolumeOverLastWeek;
    private BigDecimal previousWeeksVolume;
    private BigDecimal nextWeeksOpen;
    private BigDecimal nextWeeksClose;
    private BigDecimal percentChangeNextWeeksPrice;
    private int daysToNextDividend;
    private BigDecimal percentReturnNextDividend;

}

