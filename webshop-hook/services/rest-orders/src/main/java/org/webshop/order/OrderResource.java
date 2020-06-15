package org.webshop.order;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/orders")
@Produces(APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOGGER = Logger.getLogger(OrderResource.class);

    @Inject
    OrderService service;

    @Operation(summary = "Returns a order for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Orders.class)))
    @GET
    @Path("/{id}")
    public Response getOrder(
            @PathParam("id") Long id) {
        Orders order = service.findOrderById(id);
        if (order != null) {
            LOGGER.debug("Found order " + order);
            return Response.ok(order).build();
        } else {
            LOGGER.debug("No order found with id " + id);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Returns all the orders")
        @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Orders.class, type = SchemaType.ARRAY)))
    @GET
    public Response getAllOrders() {
        List<Orders> orders = service.findAllOrders();
        LOGGER.debug("Total number of products " + orders);
        return Response.ok(orders).build();
    }

    @Operation(summary = "Creates a valid order")
    @APIResponse(responseCode = "201", description = "The URI of the created order", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createOrder(
            @Valid Orders order, @Context UriInfo uriInfo) {
        order = service.persistOrder(order);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(order.id));
        LOGGER.debug("New order created with URI " + builder.build().toString());
        return Response.created(builder.build()).build();
    }



    @Operation(summary = "Deletes an exiting order")
    @APIResponse(responseCode = "204")
    @DELETE
    @Path("/{id}")
    public Response deleteOrder(
            @PathParam("id") Long id) {
        service.deleteOrder(id);
        LOGGER.debug("Order deleted with " + id);
        return Response.noContent().build();
    }

}