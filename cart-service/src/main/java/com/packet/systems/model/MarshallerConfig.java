package com.packet.systems.model;

import org.infinispan.protostream.MessageMarshaller;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MarshallerConfig {

    @Produces
    MessageMarshaller promotionMarshaller() {
        return new PromotionMarshaller();
    }

    @Produces
    MessageMarshaller productMarshaller() {
        return new ProductMarshaller();
    }

    @Produces
    MessageMarshaller shoppingCartMarshaller() {
        return new ShoppingCartMarshaller();
    }

    @Produces
    MessageMarshaller shoppingCartItemMarshaller() {
        return new ShoppingCartItemMarshaller();
    }
}
