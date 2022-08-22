package ib.api.validate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SpamInfo {
    Long seq;
    Long clientCode;
    String contents;
}
