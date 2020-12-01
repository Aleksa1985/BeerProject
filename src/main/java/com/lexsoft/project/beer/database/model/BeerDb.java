package com.lexsoft.project.beer.database.model;

import lombok.*;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "beer")
@NoArgsConstructor
@AllArgsConstructor
public class BeerDb extends BaseEntity<Long> {

    @Column(columnDefinition = "LONGTEXT")
    String description;
    String name;

    @OneToOne(mappedBy = "beer",fetch = FetchType.LAZY)
    ProviderBeerDb providerBeerDb;

    @OneToMany(mappedBy = "beer", fetch = FetchType.EAGER)
    List<TemperatureDb> temperatureList;

    @Builder(toBuilder = true)
    public BeerDb(Long id, Date created, Date updated, String description, String name,
                  List<TemperatureDb> temperatureList,ProviderBeerDb providerBeerDb) {
        super(id, created, updated);
        this.description = description;
        this.name = name;
        this.temperatureList = temperatureList;
        this.providerBeerDb = providerBeerDb;
    }
}
