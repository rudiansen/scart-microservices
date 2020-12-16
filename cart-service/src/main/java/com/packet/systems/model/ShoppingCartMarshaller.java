package com.packet.systems.model;

import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartMarshaller implements MessageMarshaller<ShoppingCart> {

    @Override
    public ShoppingCart readFrom(ProtoStreamReader reader) throws IOException {
        double cartItemTotal = reader.readDouble("cartItemTotal");
        double cartItemPromoSavings = reader.readDouble("cartItemPromoSavings");
        double shippingTotal = reader.readDouble("shippingTotal");
        double shippingPromoSavings = reader.readDouble("shippingPromoSavings");
        double cartTotal = reader.readDouble("cartTotal");
        String cartId = reader.readString("cartId");
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();
        shoppingCartItemList = reader.readCollection("shoppingCartItemList", shoppingCartItemList, ShoppingCartItem.class);

        return new ShoppingCart(cartItemTotal, cartItemPromoSavings, shippingTotal, shippingPromoSavings, cartTotal,
                cartId, shoppingCartItemList);
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, ShoppingCart shoppingCart) throws IOException {

        writer.writeDouble("cartItemTotal", shoppingCart.getCartItemTotal());
        writer.writeDouble("cartItemPromoSavings", shoppingCart.getCartItemPromoSavings());
        writer.writeDouble("shippingTotal", shoppingCart.getShippingTotal());
        writer.writeDouble("shippingPromoSavings", shoppingCart.getShippingPromoSavings());
        writer.writeDouble("cartTotal", shoppingCart.getCartTotal());
        writer.writeString("cartId", shoppingCart.getCartId());
        writer.writeCollection("shoppingCartItemList", shoppingCart.getShoppingCartItemList(), ShoppingCartItem.class);
    }

    @Override
    public Class<? extends ShoppingCart> getJavaClass() {
        return ShoppingCart.class;
    }

    @Override
    public String getTypeName() {
        return "coolstore.ShoppingCart";
    }
}
