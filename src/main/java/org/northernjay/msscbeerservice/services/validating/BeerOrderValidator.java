package org.northernjay.msscbeerservice.services.validating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.northernjay.msscbeerservice.repositories.BeerRepository;
import org.northernjay.sfg.brewery.model.events.BeerOrderDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto) {

        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(orderLine -> {
            if (beerRepository.findByUpc(orderLine.getUpc()) == null) {
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.get() == 0;
    }
}
