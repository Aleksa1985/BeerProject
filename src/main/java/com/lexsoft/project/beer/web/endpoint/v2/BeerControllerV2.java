package com.lexsoft.project.beer.web.endpoint.v2;

import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.core.service.BeerService;
import com.lexsoft.project.beer.core.validation.aspects.Authorized;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.web.dto.beer.BeerWebDto;
import com.lexsoft.project.beer.web.dto.information.InformationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lexsoft.project.beer.web.dto.MessageContainer.BEER_ASYNC_INFO;

@Controller
@RequestMapping("/v2/beers")
public class BeerControllerV2 {

    @Autowired
    BeerService beerService;
    @Autowired
    Converter<BeerDb, BeerWebDto> converter;

    @GetMapping
    @Authorized(roles = {"admin","user"})
    public ResponseEntity<List<BeerWebDto>> getAllBeers() {
        List<BeerDb> allBeers = beerService.findAllBeers();
        List<BeerWebDto> beersWebDto = converter.convertList(allBeers);
        return ResponseEntity.ok(beersWebDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BeerWebDto> getBeerById(@PathVariable("id") Integer id) {
        BeerDb beer = beerService.findBeerById(Long.valueOf(id));
        return ResponseEntity.ok(Optional.ofNullable(beer)
                .map(e -> converter.convert(e))
                .orElse(null));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteBeer(@PathVariable("id") Integer id) {
        beerService.deleteBeer(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAllBeers(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        beerService.deleteAllBeers(null);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity createBeers() {
        beerService.saveBeers();
        return ResponseEntity.ok(InformationDto.builder()
                .message(BEER_ASYNC_INFO)
                .location("/v1/beers")
                .build());
    }

}

