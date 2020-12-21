package com.packet.systems;

import com.packet.systems.model.ShoppingCart;
import com.packet.systems.model.order.Order;
import com.packet.systems.service.ShoppingCartService;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.Json;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@Path("/api/cart")
public class CartResource {

    private static final Logger log = LoggerFactory.getLogger(CartResource.class);

    @ConfigProperty(name = "mp.messaging.outgoing.orders.bootstrap.servers")
    public String bootstrapServers;

    @ConfigProperty(name = "mp.messaging.outgoing.orders.topic")
    public String ordersTopic;

    @ConfigProperty(name = "mp.messaging.outgoing.orders.value.serializer")
    public String ordersTopicValueSerializer;

    @ConfigProperty(name = "mp.messaging.outgoing.orders.key.serializer")
    public String ordersTopicKeySerializer;

    private Producer<String, String> producer;
    
    @Inject
    ShoppingCartService shoppingCartService;

    @GET
    @Path("/{cartId}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Get the content of cart by cartId")
    public ShoppingCart getCart(@PathParam("cartId") String cartId) {
        return shoppingCartService.getShoppingCart(cartId);
    }

    @POST
    @Path("/{cartId}/{itemId}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add an item with its quantity")
    public ShoppingCart add(@PathParam("cartId") String cartId,
                            @PathParam("itemId") String itemId,
                            @PathParam("quantity") int quantity) throws Exception {
        return shoppingCartService.addItem(cartId, itemId, quantity);
    }

    @POST
    @Path("/{cartId}/{tmpId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Set the cart with a new Id")
    public ShoppingCart set(@PathParam("cartId") String cartId,
                            @PathParam("tmpId") String tmpId) throws Exception {
        return shoppingCartService.set(cartId, tmpId);
    }

    @DELETE
    @Path("/{cartId}/{itemId}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a quantity or item from the cart")
    public ShoppingCart delete(@PathParam("cartId") String cartId,
                               @PathParam("itemId") String itemId,
                               @PathParam("quantity") int quantity) throws Exception {
        return shoppingCartService.deleteItem(cartId, itemId, quantity);
    }

    @POST
    @Path("/checkout/{cartId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Checkout")
    public ShoppingCart checkout(@PathParam("cartId") String cartId, Order order) {
        sendOrder(order, cartId);
        return shoppingCartService.checkout(cartId);
    }

    private void sendOrder(Order order, String cartId) {
        order.setTotal(shoppingCartService.getShoppingCart(cartId).getCartTotal() + "");
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(ordersTopic, null,
                null, null, Json.encode(order), new RecordHeaders().add("content-type", "application/json".getBytes()));
        producer.send(producerRecord);
        log.info("Sent message: " + Json.encode(order));
    }

    public void init(@Observes StartupEvent ev) {
        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);
        props.put("value.serializer", ordersTopicValueSerializer);
        props.put("key.serializer", ordersTopicKeySerializer);
        producer = new KafkaProducer<String, String>(props);
    }
}
