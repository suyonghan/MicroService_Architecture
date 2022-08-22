package ib.api.validate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@Setter
@ToString
@Builder
@ResponseBody
public class Ping {

    @JsonProperty
    private String serviceName;

    @JsonProperty
    private String instanceId;
}
