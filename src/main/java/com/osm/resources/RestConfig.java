/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.resources;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;

@ApplicationPath("/rest/v1/")
public class RestConfig extends ResourceConfig {

    @Inject
    private Logger log;

    public RestConfig() {
        packages("com.osm.resources;com.osm.providers");
        register(JacksonFeature.class);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("Deploy App!");
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("Undeploy app!");
    }
}
