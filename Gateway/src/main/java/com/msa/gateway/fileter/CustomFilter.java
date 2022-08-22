package com.msa.gateway.fileter;

import com.msa.gateway.config.filter.CustomFilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilterConfig> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CustomFilter() {
        super(CustomFilterConfig.class);
    }

    @Override
    public GatewayFilter apply(CustomFilterConfig config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            logger.info("{} post filter request-id : {}", this.getClass().getSimpleName(), request.getId());
            logger.info("{} post filter request uri : {}", this.getClass().getSimpleName(), request.getURI());

            return chain.filter(exchange).then((Mono.fromRunnable(() -> {
                logger.info("{} post filter : response code : {}", this.getClass().getSimpleName(), response.getStatusCode());
            })));
        }));
    }
}
