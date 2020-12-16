package com.packet.systems;

import com.packet.systems.model.Order;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    public List<Order> list() {
        return orderService.list();
    }

    @POST
    public List<Order> addOrder(Order order) {
        orderService.add(order);
        return list();
    }

    @GET
    @Path("/{orderId}/{status}")
    public List<Order> updateStatus(@PathParam("orderId") String orderId, @PathParam("status") String status) {
        orderService.updateStatus(orderId, status);
        return list();
    }
}