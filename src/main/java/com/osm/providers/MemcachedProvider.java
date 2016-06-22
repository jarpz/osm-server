/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.providers;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import net.spy.memcached.MemcachedClient;

@ApplicationScoped
public class MemcachedProvider {

    @Inject
    private Logger log;

    private MemcachedClient mcc;

    @Produces
    public MemcachedClient get() {
        if (mcc == null) {
            throw new UnsupportedOperationException();
        }
        return mcc;
    }

    @PostConstruct
    private void init() {
        try {
            mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));

            log.log(Level.INFO, "Memcached connection created!");
        } catch (Throwable throwable) {
            log.log(Level.SEVERE, "Memcached not created", throwable);
        }
    }

    @PreDestroy
    private void onDetach() {
        if (mcc != null) {
            mcc.shutdown();
        }
    }
}
