package com.packet.systems.codec;

import com.mongodb.MongoClientSettings;
import com.packet.systems.model.Order;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class OrderCodec implements CollectibleCodec<Order> {

    private final Codec<Document> documentCodec;

    public OrderCodec() {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public Order generateIdIfAbsentFromDocument(Order order) {
        if(!documentHasId(order)) {
            order.setOrderId(UUID.randomUUID().toString());
        }
        return order;
    }

    @Override
    public boolean documentHasId(Order order) {
        return order.getOrderId() != null;
    }

    @Override
    public BsonValue getDocumentId(Order order) {
        return new BsonString(order.getOrderId());
    }

    @Override
    public Order decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        Order order = new Order();
        if (document.getString("orderId") != null) {
            order.setOrderId(document.getString("orderId"));
        }
        order.setName(document.getString("name"));
        order.setTotal(document.getString("total"));
        order.setCcNumber(document.getString("ccNumber"));
        order.setCcExp(document.getString("ccExp"));
        order.setBillingAddress(document.getString("billingAddress"));
        order.setStatus(document.getString("status"));
        return order;
    }

    @Override
    public void encode(BsonWriter writer, Order order, EncoderContext encoderContext) {
        Document doc = new Document();
        doc.put("orderId", order.getOrderId());
        doc.put("name", order.getName());
        doc.put("total", order.getTotal());
        doc.put("ccNumber", order.getCcNumber());
        doc.put("ccExp", order.getCcExp());
        doc.put("billingAddress", order.getBillingAddress());
        doc.put("status", order.getStatus());
        documentCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Order> getEncoderClass() {
        return Order.class;
    }
}
