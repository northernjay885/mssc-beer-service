package org.northernjay.msscbeerservice.web.mappers;


import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.northernjay.msscbeerservice.domain.Beer;
import org.northernjay.sfg.brewery.model.BeerDto;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

}
