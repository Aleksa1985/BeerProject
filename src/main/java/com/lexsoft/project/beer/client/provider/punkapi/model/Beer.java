package com.lexsoft.project.beer.client.provider.punkapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lexsoft.project.beer.client.provider.punkapi.ProviderBeer;
import lombok.*;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Beer extends ProviderBeer {

    private BeerMethod method;

    @Builder
    public Beer(String name, String description, String id, BeerMethod method) {
        super(name, description, id);
        this.method = method;
    }
}
