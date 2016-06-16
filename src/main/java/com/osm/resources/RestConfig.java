
package com.osm.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/rest/v1")
public class RestConfig extends ResourceConfig {

    @Inject
    private Logger log;

    public RestConfig() {
        packages("com.osm.resources;com.osm.providers");
        register(JacksonFeature.class);
        register(RolesAllowedDynamicFeature.class);
    }

    @PostConstruct
    public void postConstruct() {
        log.log(Level.INFO, "Deploy App!");
    }

    @PreDestroy
    public void preDestroy() {
        log.log(Level.INFO, "Undeploy app!");
    }
}
