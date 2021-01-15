package org.northernjay.msscbeerservice.services;


import org.northernjay.sfg.brewery.model.BeerDto;
import org.northernjay.sfg.brewery.model.BeerPagedList;
import org.northernjay.sfg.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId, boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean showInventoryOnHand, PageRequest pageRequest);

    BeerDto getByUpc(String upc);
}
