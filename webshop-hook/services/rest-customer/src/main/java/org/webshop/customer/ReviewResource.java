package org.webshop.customer;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/reviews")
@Produces(APPLICATION_JSON)
public class ReviewResource {

        private static final Logger LOGGER = Logger.getLogger(ReviewResource.class);

        @Inject
        ReviewService service;

    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Review.class)))
        @GET
        @Path("/{id}")
        public Response getCustomer(
                @PathParam("id") Long id) {
            List<Review> review = service.findReviewsByProduct(id);
            if (review != null) {
                LOGGER.debug("Found customer " + review);
                return Response.ok(review).build();
            } else {
                LOGGER.debug("No customer found with id " + id);
                return Response.noContent().build();
            }
        }

    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Review.class)))
    @GET
    @Path("/average/{id}")
    public Response getReviewStatsByProductId(
            @PathParam("id") Long id) {
        Stats stats = service.getReviewStats(id);
        if (stats != null) {
            LOGGER.debug("Found customer " + stats);
            return Response.ok(stats).build();
        } else {
            LOGGER.debug("No customer found with id " + id);
            return Response.noContent().build();
        }
    }


    @APIResponse(responseCode = "201", description = "The URI of the created customer", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
        @POST
        public Response createCustomer(
            @Valid Review review, @Context UriInfo uriInfo) {
            review = service.persistReview(review);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(review.id));
            LOGGER.debug("New customer created with URI " + builder.build().toString());
            return Response.created(builder.build()).build();
        }


    @APIResponse(responseCode = "200", description = "The updated customer", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Review.class)))
        @PUT
        public Response updateCustomer(
                @Valid Review review) {
            review = service.updateCustomer(review);
            LOGGER.debug("Customer updated with new valued " + review);
            return Response.ok(review).build();
        }

    @APIResponse(responseCode = "204")
        @DELETE
        @Path("/{id}")
        public Response deleteCustomer(
                @PathParam("id") Long id) {
            service.deleteCustomer(id);
            LOGGER.debug("Customer deleted with " + id);
            return Response.noContent().build();
        }

    }