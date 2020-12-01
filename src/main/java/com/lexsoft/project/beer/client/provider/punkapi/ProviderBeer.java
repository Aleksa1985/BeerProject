package com.lexsoft.project.beer.client.provider.punkapi;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ProviderBeer {

    private String name;
    private String description;
    private String id;

    public ProviderBeer(String name, String description, String id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }
}
