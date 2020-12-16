package com.packet.systems;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.packet.systems.model.ProductMarshaller;
import com.packet.systems.model.PromotionMarshaller;
import com.packet.systems.model.ShoppingCart;
import com.packet.systems.model.ShoppingCartItemMarshaller;
import com.packet.systems.model.ShoppingCartMarshaller;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class Producers {

    @Inject @ConfigProperty(name = "jdg.host", defaultValue = "localhost")
    String dgHost;

    @Inject @ConfigProperty(name = "jdg.port", defaultValue = "11222")
    int dgPort;

    @Produces
    MessageMarshaller promotionMarshaller() {
        return new PromotionMarshaller();
    }

    @Produces
    MessageMarshaller productMarshaller() {
        return new ProductMarshaller();
    }

    @Produces
    MessageMarshaller shoppingCartMarshaller() {
        return new ShoppingCartMarshaller();
    }

    @Produces
    MessageMarshaller shoppingCartItemMarshaller() {
        return new ShoppingCartItemMarshaller();
    }

    @Produces
    Gson createGsonObject() {
        return new Gson();
    }

    @Produces
    JsonParser createJsonParser() {
        return new JsonParser();
    }

    @Produces
    RemoteCache<String, ShoppingCart> getCache() throws IOException {

        RemoteCacheManager manager = new RemoteCacheManager(getConfigBuilder().build());

        SerializationContext serCtx = ProtoStreamMarshaller.getSerializationContext(manager);
        FileDescriptorSource fds = new FileDescriptorSource();
        fds.addProtoFiles("META-INF/cart.proto");
        serCtx.registerProtoFiles(fds);
        serCtx.registerMarshaller(new ShoppingCartMarshaller());
        serCtx.registerMarshaller(new ShoppingCartItemMarshaller());
        serCtx.registerMarshaller(new ProductMarshaller());
        serCtx.registerMarshaller(new PromotionMarshaller());
        return manager.getCache();
    }

    protected ConfigurationBuilder getConfigBuilder() {
        ConfigurationBuilder cfg = null;
        cfg = new ConfigurationBuilder().addServer()
                .host(dgHost)
                .port(dgPort)
                .marshaller(new ProtoStreamMarshaller())
                .clientIntelligence(ClientIntelligence.BASIC);
        return cfg;
    }
}
