package org.northernjay.msscbeerservice.services;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.northernjay.msscbeerservice.domain.Beer;
import org.northernjay.msscbeerservice.repositories.BeerRepository;
import org.northernjay.msscbeerservice.web.controller.NotFoundException;
import org.northernjay.msscbeerservice.web.mappers.BeerMapper;
import org.northernjay.sfg.brewery.model.BeerDto;
import org.northernjay.sfg.brewery.model.BeerPagedList;
import org.northernjay.sfg.brewery.model.BeerStyleEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        } else {
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {

        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beerToUpdate = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beerToUpdate.setBeerName(beerDto.getBeerName());
        beerToUpdate.setBeerStyle(beerDto.getBeerStyle().name());
        beerToUpdate.setPrice(beerDto.getPrice());
        beerToUpdate.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate));
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean showInventoryOnHand, PageRequest pageRequest) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        System.out.println("I was called");

        if (!StringUtils.isEmpty(beerName) && beerStyle != null) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && beerStyle == null) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && beerStyle != null) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beer -> {
                    if (showInventoryOnHand) {
                        return beerMapper.beerToBeerDtoWithInventory(beer);
                    } else {
                        return beerMapper.beerToBeerDto(beer);
                    }
                })
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc")
    @Override
    public BeerDto getByUpc(String upc) {
        System.out.println("Call from database");
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
