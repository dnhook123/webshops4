package org.webshop.cart;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

@ApplicationScoped
class CartApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(CartApplicationLifeCycle.class));

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("the microservice Cart is starting");
        LOGGER.info(String.format("the profile mode is `%s`", ProfileManager.getActiveProfile()));
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The microservice Cart is stopping...");
    }
}
