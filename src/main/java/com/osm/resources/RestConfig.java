package com.osm.resources;

import com.osm.providers.JacksonProvider;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import org.apache.log4j.Logger;
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
        register(JacksonProvider.class);
        register(RolesAllowedDynamicFeature.class);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Deploy App!");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("Undeploy app!");
    }
}
