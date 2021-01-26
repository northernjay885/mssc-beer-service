package org.northernjay.msscbeerservice.services.validating;

import guru.sfg.brewery.model.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.northernjay.msscbeerservice.config.JmsConfig;
import org.northernjay.sfg.brewery.model.events.ValidateOrderResult;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateOrderListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest request) throws JMSException {

        Boolean isValid = beerOrderValidator.validateOrder(request.getBeerOrder());
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
        .orderId(request.getBeerOrder().getId())
        .isValid(isValid)
        .build());

    }


}
