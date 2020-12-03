package com.packet.systems;

import com.packet.systems.model.Inventory;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/inventory")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @GET
    public List<Inventory> getAll() {
        return Inventory.listAll();
    }

    @GET
    @Path("{itemId}")
    public List<Inventory> getAvailability(@PathParam("itemId") String itemId) {
        return Inventory.<Inventory>streamAll()
                .filter(i -> i.itemId.equals(itemId))
                .collect(Collectors.toList());
    }

    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception e) {
            int code = 500;

            if (e instanceof WebApplicationException) {
                code = ((WebApplicationException) e).getResponse().getStatus();
            }

            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", e.getMessage()).add("code", code).build())
                    .build();
        }
    }
}
