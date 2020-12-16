package com.packet.systems.model;

import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;

public class PromotionMarshaller implements MessageMarshaller<Promotion> {
    @Override
    public Promotion readFrom(ProtoStreamReader reader) throws IOException {
        String itemId = reader.readString("itemId");
        double percentOff = reader.readDouble("percentOff");
        return new Promotion(itemId, percentOff);
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, Promotion promotion) throws IOException {
        writer.writeString("itemId", promotion.getItemId());
        writer.writeDouble("percentOff", promotion.getPercentOff());
    }

    @Override
    public Class<? extends Promotion> getJavaClass() {
        return Promotion.class;
    }

    @Override
    public String getTypeName() {
        return "coolstore.Promotion";
    }
}
