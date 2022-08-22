package ib.api.validate.controller;

import ib.api.validate.config.AppConfig;
import ib.api.validate.model.Ping;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final AppConfig config;

    @GetMapping("/ping")
    public ResponseEntity<Ping> checkPing(HttpServletRequest request){
        logger.info("remote-host : {}", request.getRemoteHost());
        return new ResponseEntity<>(Ping.builder()
                .serviceName(config.getServiceName())
                .instanceId(config.getInstanceId())
                .build(), null, HttpStatus.OK);
    }
}
