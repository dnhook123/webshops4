package org.webshop.cart;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Body;
import org.webshop.cart.client.Product;

import javax.inject.Inject;
import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/carts")
@Produces(APPLICATION_JSON)
public class CartController {

        private static final Logger LOGGER = Logger.getLogger(CartController.class);

        @Inject
        CartService service;

    @Operation(summary = "Returns a cart for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Cart.class)))
        @GET
        @Path("/{id}")
        public Response getCart(
                @PathParam("id") Long id) {
            Cart cart = service.findCartById(id);
            if (cart != null) {
                LOGGER.debug("Found cart " + cart);
                return Response.ok(cart).build();
            } else {
                LOGGER.debug("No cart found with id " + id);
                return Response.noContent().build();
            }
        }

    @Operation(summary = "Creates a valid cart")
    @APIResponse(responseCode = "201", description = "The URI of the created cart", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
        @POST
        public Response createCart(@Valid Cart cart, @Context UriInfo uriInfo) {
            if(service.CartForCustomerExists(cart.customerId)){
                return Response.noContent().build();
            }
            cart = service.persistCart(cart);

          return Response.ok(cart).build();
        }

   @Operation(summary = "Add a product to a cart")
   @APIResponse(responseCode = "200", description = "The cart with the added product", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Cart.class)))
        @PUT
        @Path("/{cartId}/addProduct/{productId}")
        public Response addProductToCart(@PathParam("cartId") Long cartId, @PathParam("productId") Long productId)
        {
            Cart cart = service.addProductToCart(cartId, productId);
            LOGGER.debug("Cart updated with new product " + productId);
            return Response.ok(cart).build();
        }
/*
    @Operation(summary = "Remove a product from a cart")
    @APIResponse(responseCode = "200", description = "The updated cart", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Cart.class)))
    @PUT
    public Response removeProductFromCart(long cartId, long productId) {
       Cart cart = service.removeProductFromCart(cartId, productId);
        LOGGER.debug("Cart updated with new valued " + cart);
        return Response.ok(cart).build();
    }*/



    }