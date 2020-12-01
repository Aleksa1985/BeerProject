package com.lexsoft.project.beer.database.model;

import lombok.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "provider")
@NoArgsConstructor
@AllArgsConstructor
public class ProviderBeerDb extends BaseEntity<Long>{

    @Column(columnDefinition = "system_id")
    String systemId;

    @OneToOne
    @JoinColumn(name = "beer_id")
    BeerDb beer;

    @Builder
    public ProviderBeerDb(Long id, Date created, Date updated,
                          BeerDb beer,String systemId) {
        super(id, created, updated);

        this.beer = beer;
        this.systemId = systemId;
    }
}
