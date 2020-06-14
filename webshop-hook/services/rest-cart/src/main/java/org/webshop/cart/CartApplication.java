package org.webshop.cart;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Cart API",
                description = "This API allows CRUD operations on a Cart",
                version = "1.0",
                contact = @Contact(name = "Quarkus", url = "https://github.com/quarkusio")),
        servers = {
                @Server(url = "http://localhost:8084")
        },
        tags = {
                @Tag(name = "Cart api", description = "Cart CRUD"),
        }
)

public class CartApplication extends Application {
}