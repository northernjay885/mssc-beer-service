package org.northernjay.msscbeerservice.events;


import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.northernjay.msscbeerservice.web.model.BeerDto;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 3744107884852637969L;

    private final BeerDto beerDto;
}
