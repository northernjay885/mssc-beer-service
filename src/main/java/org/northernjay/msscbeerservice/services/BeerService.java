package org.northernjay.msscbeerservice.services;


import org.northernjay.msscbeerservice.web.model.BeerDto;
import org.northernjay.msscbeerservice.web.model.BeerPagedList;
import org.northernjay.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);
}
