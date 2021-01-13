package org.northernjay.msscbeerservice.events;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.northernjay.msscbeerservice.web.model.BeerDto;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 3744107884852637969L;

    private BeerDto beerDto;
}
