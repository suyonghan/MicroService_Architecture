package ib.api.validate.mapper;

import ib.api.validate.model.SpamInfo;

import java.util.List;

public interface SpamInfoMapper {
    SpamInfo selectSpamInfoClientCodeSeq(Long clientCode, Long seq) throws Exception;
    List<SpamInfo> selectSpamInfoClientCode(Long clientCode) throws Exception;
}
