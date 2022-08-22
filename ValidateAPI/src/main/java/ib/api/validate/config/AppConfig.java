package ib.api.validate.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    @Value("${spring.application.name}")
    String serviceName;

    @Value("${eureka.instance.instance-id}")
    String instanceId;

    @Value("${generics.common.mapper-path}")
    String mapperPath;
}
