/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.spy.memcached.CachedData;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;
import org.apache.log4j.Logger;

@ApplicationScoped
public class MemCachedService extends MemcachedClient {

//    @Resource(name = "url/memcached")
//    private String memcached;
    @Inject
    private ObjectMapper mapper;
    @Inject
    private Logger log;

    public MemCachedService() throws IOException {
        super(new InetSocketAddress("127.0.0.1", 11211));
    }

    @Override
    protected void finalize() throws Throwable {
        this.shutdown();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Logger getLog() {
        return log;
    }

    public class JsonTranscoder<T> implements Transcoder<T> {

        private static final int FLAGS = 0;

        private Class<T> clasz;
        private ObjectMapper objMapper;
        private Logger log;

        public JsonTranscoder(Class<T> clasz) {
            this.clasz = clasz;
            this.objMapper = MemCachedService.this.getMapper();
            this.log = MemCachedService.this.getLog();
        }

        @Override
        public boolean asyncDecode(CachedData d) {
            return false;
        }

        @Override
        public CachedData encode(T obj) {
            try {
                return new CachedData(FLAGS, objMapper.writeValueAsBytes(obj), getMaxSize());
            } catch (Throwable throwable) {
                log.error("encode()", throwable);
                return null;
            }
        }

        @Override
        public T decode(CachedData data) {
            try {
                return objMapper.readValue(data.getData(), clasz);
            } catch (Throwable throwable) {
                log.error("decode()", throwable);
                return null;
            }
        }

        @Override
        public int getMaxSize() {
            return CachedData.MAX_SIZE;
        }
    }
}
