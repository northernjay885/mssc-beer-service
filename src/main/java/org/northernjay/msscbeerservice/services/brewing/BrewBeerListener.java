package org.northernjay.msscbeerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.northernjay.msscbeerservice.config.JmsConfig;
import org.northernjay.msscbeerservice.domain.Beer;
import org.northernjay.msscbeerservice.repositories.BeerRepository;
import org.northernjay.sfg.brewery.model.BeerDto;
import org.northernjay.sfg.brewery.model.events.BrewBeerEvent;
import org.northernjay.sfg.brewery.model.events.NewInventoryEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
