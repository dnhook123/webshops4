package org.webshop.customer;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

@ApplicationScoped
class ReviewApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ReviewApplicationLifeCycle.class));

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("the microservice product is starting");
        LOGGER.info(String.format("the profile mode is `%s`", ProfileManager.getActiveProfile()));
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The microservice product is stopping...");
    }
}
