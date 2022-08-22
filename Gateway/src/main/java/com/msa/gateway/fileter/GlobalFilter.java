package com.msa.gateway.fileter;

import com.msa.gateway.config.filter.GlobalFilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilterConfig> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public GlobalFilter(){
        super(GlobalFilterConfig.class);
    }

    @Override
    public GatewayFilter apply(GlobalFilterConfig config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            logger.info("Global Filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()){
                logger.info("Global Filter Start : request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()){
                    logger.info("Global Filter End : response code -> {}", response.getStatusCode());
                }
                logger.info("Custom Post");
            }));
        });
    }
}
