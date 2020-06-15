package org.webshop;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.webshop.order.Orders;

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
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderResourceTest {



    private static final String DEFAULT_ORDERDATE = "Super Baguettee";

    private static final String DEFAULT_DELIVERY = "Super Baguette Tradition";

    private static final String DEFAULT_SHIPPINGADDRESS = "super_baguette.png";

    private static final String DEFAULT_ORDERSTATUS = "eats baguette really quickly";

    private  static final BigDecimal DEFAULT_PRICE = new BigDecimal(13);

    private static String ORDER_ID;


    @Test
    void shouldNotGetUnknownOrder() {
        given()
                .pathParam("id", 99)
                .when().get("/api/orders/{id}")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }


    @Test
    void shouldAddOrderWithMissingDeliveryTime() {
        Orders order = new Orders();
        order.orderDate = DEFAULT_ORDERDATE;
        order.deliveryTime = null;
        order.shippingAddress = DEFAULT_SHIPPINGADDRESS;
        order.orderStatus = DEFAULT_ORDERSTATUS;
        order.totalPrice = DEFAULT_PRICE;

        given()
                .body(order)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(CREATED.getStatusCode());
    }


    @Test
    @Order(1)
    void shouldAddOrder() {
        Orders order = new Orders();
        order.orderDate = DEFAULT_ORDERDATE;
        order.deliveryTime = DEFAULT_DELIVERY;
        order.shippingAddress = DEFAULT_SHIPPINGADDRESS;
        order.orderStatus = DEFAULT_ORDERSTATUS;
        order.totalPrice = DEFAULT_PRICE;

        String location = given()
                .body(order)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().header("Location");
        assertTrue(location.contains("/api/orders"));

        // Stores the id
        String[] segments = location.split("/");
        ORDER_ID = segments[segments.length - 1];
        assertNotNull(ORDER_ID);

        given()
                .pathParam("id",  ORDER_ID)
                .when().get("/api/orders/{id}")
                .then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body("orderDate", Is.is(DEFAULT_ORDERDATE))
                .body("deliveryTime", Is.is(DEFAULT_DELIVERY))
                .body("shippingAddress", Is.is(DEFAULT_SHIPPINGADDRESS));

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