package com.lexsoft.project.beer.web.dto.beer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalBeerTemperatureDto {

    Double average;
    List<TemperatureDto> temperatures;




}
