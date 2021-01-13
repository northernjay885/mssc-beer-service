package org.northernjay.msscbeerservice.events;

import lombok.NoArgsConstructor;
import org.northernjay.msscbeerservice.web.model.BeerDto;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
