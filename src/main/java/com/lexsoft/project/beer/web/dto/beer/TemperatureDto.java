package com.lexsoft.project.beer.web.dto.beer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureDto {

    String unit;
    Integer duration;
    Integer value;

}
