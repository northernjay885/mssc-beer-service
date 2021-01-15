package org.northernjay.msscbeerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.northernjay.msscbeerservice.config.JmsConfig;
import org.northernjay.msscbeerservice.domain.Beer;
import org.northernjay.msscbeerservice.repositories.BeerRepository;
import org.northernjay.msscbeerservice.services.BeerInventoryService;
import org.northernjay.msscbeerservice.web.mappers.BeerMapper;
import org.northernjay.sfg.brewery.model.events.BrewBeerEvent;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);

            if (invQOH <= beer.getMinOnHand()) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
