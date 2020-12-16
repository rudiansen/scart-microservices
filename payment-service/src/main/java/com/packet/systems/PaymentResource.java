package com.packet.systems;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@Path("/")
@Singleton
public class PaymentResource {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentResource.class);

    private Producer<String, String> producer;

    @ConfigProperty(name = "mp.messaging.outgoing.payments.bootstrap.servers")
    public String bootstrapServers;

    @ConfigProperty(name = "mp.messaging.outgoing.payments.topic")
    public String paymentsTopic;

    @ConfigProperty(name = "mp.messaging.outgoing.payments.value.serializer")
    public String paymentsTopicValueSerializer;

    @ConfigProperty(name = "mp.messaging.outgoing.payments.key.serializer")
    public String paymentsTopicKeySerializer;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void handleCloudEvent(String payload) {
        String orderId = "unknown";
        String paymentId = "" + ((int) (Math.floor(Math.random() * 100000)));

        try {
            LOG.info("Received event = {}", payload);
            JsonObject event = new JsonObject(payload);
            orderId = event.getString("orderId");
            String total = event.getString("total");
            JsonObject ccDetails = event.getJsonObject("creditCard");
            String name = event.getString("name");

            // Fake processing time
            Thread.sleep(5000);
            if (!ccDetails.getString("number").startsWith("4")) {
                fail(orderId, paymentId, "Invalid Credit Card: " + ccDetails.getString("number"));
            } else {
                pass(orderId, paymentId, "Payment of " + total + " succeeded for " + name + " CC Details: " + ccDetails.toString());
            }
        } catch (Exception e) {
            fail(orderId, paymentId, "Unknown error: " + e.getMessage() + " for payment: " + payload);
        }
    }

    private void pass(String orderId, String paymentId, String remarks) {
        JsonObject payload = new JsonObject();
        payload.put("orderId", orderId);
        payload.put("paymentId", paymentId);
        payload.put("remarks", remarks);
        payload.put("status", "COMPLETED");

        LOG.info("Sending payment success: " + payload.toString());
        producer.send(new ProducerRecord<String, String>(paymentsTopic, payload.toString()));
    }

    private void fail(String orderId, String paymentId, String remarks) {
        JsonObject payload = new JsonObject();
        payload.put("orderId", orderId);
        payload.put("paymentId", paymentId);
        payload.put("remarks", remarks);
        payload.put("status", "FAILED");

        LOG.info("Sending payment failure: " + payload.toString());
        producer.send(new ProducerRecord<String, String>(paymentsTopic, payload.toString()));
    }

    public void init(@Observes StartupEvent ev) {
        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);
        props.put("value.serializer", paymentsTopicValueSerializer);
        props.put("key.serializer", paymentsTopicKeySerializer);
        producer = new KafkaProducer<String, String>(props);
    }
}