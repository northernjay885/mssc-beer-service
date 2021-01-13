package org.northernjay.msscbeerservice.events;

import lombok.NoArgsConstructor;
import org.northernjay.msscbeerservice.web.model.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
