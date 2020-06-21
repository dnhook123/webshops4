package org.webshop.product;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/products")
@Produces(APPLICATION_JSON)
public class ProductResource {

        private static final Logger LOGGER = Logger.getLogger(ProductResource.class);

        @Inject
        ProductService service;

    @Operation(summary = "Returns a random product")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Product.class, required = true)))     @GET
        @Path("/random")
        public Response getRandomProduct() {
            Product product = service.findRandomProduct();
            LOGGER.debug("Found random product " + product);
            return Response.ok(product).build();
        }

    @Operation(summary = "Returns all the products from the database")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Product.class, type = SchemaType.ARRAY)))
        @GET
        public Response getAllProductes() {
            List<Product> products = service.findAllProducts();
            LOGGER.debug("Total number of products " + products);
            return Response.ok(products).build();
        }

    @Operation(summary = "Returns a product for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Product.class)))
        @GET
        @Path("/{id}")
        public Response getProduct(
                @PathParam("id") Long id) {
            Product product = service.findProductById(id);
            if (product != null) {
                LOGGER.debug("Found product " + product);
                return Response.ok(product).build();
            } else {
                LOGGER.debug("No product found with id " + id);
                return Response.noContent().build();
            }
        }

    @Operation(summary = "Creates a valid product")
    @APIResponse(responseCode = "201", description = "The URI of the created product", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
        public Response createProduct(@Valid Product product, @Context UriInfo uriInfo) {
            product = service.persistProduct(product);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(product.id));
            LOGGER.debug("New product created with URI " + builder.build().toString());
            return Response.created(builder.build()).build();
        }

    @Operation(summary = "Updates an exiting product")
    @APIResponse(responseCode = "200", description = "The updated product", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Product.class)))
    @PUT
        @Path("/{id}")
        public Response updateProduct(@PathParam("id") Long id, Product newProduct) {
         Product product = service.findProductById(id);
            product = service.updateProduct(newProduct);
            LOGGER.debug("Product updated with new valued " + newProduct);
            return Response.ok(product).build();
        }

    @Operation(summary = "Deletes an exiting product")
    @APIResponse(responseCode = "204")
        @DELETE
        @Path("/{id}")
        public Response deleteProduct(
                @PathParam("id") Long id) {
            service.deleteProduct(id);
            LOGGER.debug("Product deleted with " + id);
            return Response.noContent().build();
        }

    @Operation(summary = "Updates an existing Product")
    @APIResponse(responseCode = "200", description = "The updated product", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Product.class)))
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, String orderStatus) {
        Product product = service.updateProduct(id, orderStatus);
        LOGGER.debug("Order updated with new valued " + product);
        return Response.ok(product).build();
    }

    }