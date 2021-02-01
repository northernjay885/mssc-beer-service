package org.northernjay.msscbeerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import org.northernjay.sfg.brewery.model.BeerInventoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements  InventoryServiceFeignClient{

    private final InventoryFailoverFeignClient failoverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(UUID id) {
        return failoverFeignClient.getOnhandInventory();
    }
}
