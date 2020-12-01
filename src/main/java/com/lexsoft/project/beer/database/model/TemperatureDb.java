package com.lexsoft.project.beer.database.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "temperature")
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureDb extends BaseEntity<Long>{

    String unit;
    Integer value;
    Integer duration;

    @ManyToOne
    BeerDb beer;

    @Builder
    public TemperatureDb(Long id, Date created, Date updated, Integer value, String unit, Integer duration, BeerDb beer) {
        super(id, created, updated);
        this.value = value;
        this.unit = unit;
        this.duration = duration;
        this.beer = beer;
    }
}
