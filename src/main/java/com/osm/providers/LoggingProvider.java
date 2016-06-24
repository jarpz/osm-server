/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.providers;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.log4j.Logger;

public class LoggingProvider {

    @Produces
    @Dependent
    public Logger getLogger(final InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getBean().getBeanClass().getSimpleName());
    }
}
