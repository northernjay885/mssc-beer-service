package org.northernjay.msscbeerservice.web.controller;


import lombok.RequiredArgsConstructor;
import org.northernjay.msscbeerservice.services.BeerService;
import org.northernjay.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;


    @GetMapping("/{beerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BeerDto getBeerById(@PathVariable UUID beerId) {
        return beerService.getById(beerId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BeerDto saveNewBeer(@Validated @RequestBody BeerDto beerDto) {

        return beerService.saveNewBeer(beerDto);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public BeerDto updateBeerById(@PathVariable UUID beerId,@Validated @RequestBody BeerDto beerDto) {

        return beerService.updateBeer(beerId, beerDto);
    }
}
