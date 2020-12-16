package com.packet.systems.service;

import io.quarkus.runtime.StartupEvent;
import org.infinispan.client.hotrod.RemoteCacheManager;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class CacheService {

    public static final String CART_CACHE = "cart";

    @Inject
    RemoteCacheManager cacheManager;

    void onStart(@Observes StartupEvent event) {
        cacheManager.administration().getOrCreateCache(CART_CACHE, "default");
    }
}
