package org.northernjay.msscbeerservice.web.controller;


import lombok.RequiredArgsConstructor;
import org.northernjay.msscbeerservice.services.BeerService;
import org.northernjay.sfg.brewery.model.BeerDto;
import org.northernjay.sfg.brewery.model.BeerPagedList;
import org.northernjay.sfg.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false)BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, showInventoryOnHand, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }


    @GetMapping("/{beerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BeerDto getBeerById(@PathVariable UUID beerId,
                               @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
        return beerService.getById(beerId, showInventoryOnHand);
    }

    @GetMapping("/beerUpc/{upc}")
    @ResponseStatus(value = HttpStatus.OK)
    public BeerDto getBeerByUpc(@PathVariable String upc) {
        return beerService.getByUpc(upc);
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
