package com.msa.gateway.config.filter;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalFilterConfig {
    String baseMessage;
    private boolean preLogger;
    private boolean postLogger;
}
