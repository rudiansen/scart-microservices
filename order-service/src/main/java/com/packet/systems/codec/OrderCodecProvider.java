package com.packet.systems.codec;

import com.packet.systems.model.Order;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class OrderCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == Order.class) {
            return (Codec<T>) new OrderCodec();
        }
        return null;
    }
}
