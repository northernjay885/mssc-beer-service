package org.northernjay.sfg.brewery.model.events;

import lombok.NoArgsConstructor;
import org.northernjay.sfg.brewery.model.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
