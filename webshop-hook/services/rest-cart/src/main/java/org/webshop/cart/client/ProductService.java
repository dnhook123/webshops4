package org.webshop.cart.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface ProductService {

    @GET
    @Path("/{id}")
    Product getProductById(@PathParam("id") Long id);
}