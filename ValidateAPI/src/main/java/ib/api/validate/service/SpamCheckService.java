package ib.api.validate.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import ib.api.validate.model.SpamInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpamCheckService {

    private final RestTemplate restTemplate;
    private final DbService service;
    private final CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public SpamInfo findBySeqClientCode(Long seq, Long clientCode) throws Exception {
        return service.findBySeqClientCode(seq, clientCode);
    }

    public SpamInfo findByClientCode(Long clientCode) throws Exception {
        return service.findByClientCode(clientCode);
    }

    public List<SpamInfo> findByClientCode(List<Long> clientCodes) throws Exception {
        return service.findByClientCode(clientCodes);
    }

    public void delete(SpamInfo spamInfo){
        service.delete(spamInfo);
    }

    @CachePut("SpamInfo")
    @HystrixCommand(
            commandKey = "spamCheckFallback",
            fallbackMethod = "spamCheckFallback",
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
        }
    )
    public List<SpamInfo> findSpamInfo(Long id){
        logger.info("call method 'findSpamInfo'");
        SpamInfo[] spamInfos = restTemplate.getForObject("http://172.16.2.207:8889/template/{templateId}", SpamInfo[].class, id);
        assert spamInfos != null;
        return Arrays.stream(spamInfos).collect(Collectors.toList());
    }

    public List<SpamInfo> findSpamInfoFallback(Long id){
        logger.info("call method 'findSpamInfoFallback'");
        Cache.ValueWrapper w = Objects.requireNonNull(cacheManager.getCache("SpamInfo")).get(id);
        if(w != null){
            return (List<SpamInfo>) w.get();
        } else {
            return new ArrayList<>();
        }
    }
}
