package org.northernjay.msscbeerservice.web.mappers;


import org.mapstruct.Mapper;
import org.northernjay.msscbeerservice.domain.Beer;
import org.northernjay.msscbeerservice.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
