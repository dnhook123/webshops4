package org.webshop.product;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductResourceTest {

    private static final String DEFAULT_NAME = "Super Baguettee";
    private static final String UPDATED_NAME = "Super Baguette (updated)";
    private static final String DEFAULT_DESCRIPTION = "Super Baguette Tradition";
    private static final String UPDATED_DESCRIPTION = "Super Baguette Tradition (updated)";
    private static final String DEFAULT_PICTURE = "super_baguette.png";
    private static final String UPDATED_PICTURE = "super_baguette_updated.png";
    private static final String DEFAULT_SPECIALITY = "eats baguette really quickly";
    private static final String UPDATED_SPECIALITY = "eats baguette really quickly (updated)";
    private  static final BigDecimal DEFAULT_PRICE = new BigDecimal(13);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(14);
    private static final int PRODUCT_COUND = 3;
    private static String PRODUCT_ID;

    @Test
    void shouldNotGetUnknownProduct() {
        Long randomId = new Random().nextLong();
        given()
                .pathParam("id", randomId)
                .when().get("/api/products/{id}")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void shouldGetRandomProduct() {
        given()
                .when().get("/api/products/random")
                .then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON);
    }

    @Test
    void shouldNotAddInvalidItem() {
        Product product = new Product();
        product.name = null;
        product.description = DEFAULT_DESCRIPTION;
        product.picture = DEFAULT_PICTURE;
        product.sauce = DEFAULT_SPECIALITY;
        product.price = DEFAULT_PRICE;

        given()
                .body(product)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/products")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(1)
    void shouldGetInitialItems() {
        List<Product> products = get("/api/products").then()
                .statusCode(OK.getStatusCode())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract().body().as(getProductTypeRef());
        assertEquals(PRODUCT_COUND, products.size());
    }

    @Test
    @Order(2)
    void shouldAddAnItem() {
        Product product = new Product();
        product.name = DEFAULT_NAME;
        product.description = DEFAULT_DESCRIPTION;
        product.picture = DEFAULT_PICTURE;
        product.sauce = DEFAULT_SPECIALITY;
        product.price = DEFAULT_PRICE;

        String location = given()
                .body(product)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/products")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().header("Location");
        assertTrue(location.contains("/api/products"));

        // Stores the id
        String[] segments = location.split("/");
        PRODUCT_ID = segments[segments.length - 1];
        assertNotNull(PRODUCT_ID);

        given()
                .pathParam("id", PRODUCT_ID)
                .when().get("/api/products/{id}")
                .then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body("name", Is.is(DEFAULT_NAME))
                .body("description", Is.is(DEFAULT_DESCRIPTION))
                .body("picture", Is.is(DEFAULT_PICTURE))
                .body("sauce", Is.is(DEFAULT_SPECIALITY));

        List<Product> products = get("/api/products").then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .extract().body().as(getProductTypeRef());
        assertEquals(PRODUCT_COUND + 1, products.size());
    }


    @Test
    @Order(3)
    void shouldRemoveAnItem() {
        given()
                .pathParam("id", PRODUCT_ID)
                .when().delete("/api/products/{id}")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());

        List<Product> products = get("/api/products").then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .extract().body().as(getProductTypeRef());
        assertEquals(PRODUCT_COUND, products.size());
    }

    private TypeRef<List<Product>> getProductTypeRef() {
        return new TypeRef<List<Product>>() {
            // Kept empty on purpose
        };
    }

    @Test
    void shouldPingOpenAPI() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
                .when().get("/openapi")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingSwaggerUI() {
        given()
                .when().get("/swagger-ui")
                .then()
                .statusCode(OK.getStatusCode());
    }
    @Test
    void shouldPingMetrics() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
                .when().get("/metrics/application")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }
    @Test
    void shouldPingLiveness() {
        given()
                .when().get("/health/live")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingReadiness() {
        given()
                .when().get("/health/ready")
                .then()
                .statusCode(OK.getStatusCode());
    }
}