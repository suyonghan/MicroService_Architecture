package ib.api.validate.service;

import ib.api.validate.mapper.SpamInfoMapper;
import ib.api.validate.model.SpamInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DbService {

    private final SpamInfoMapper mapper;

    public SpamInfo findByClientCode(Long clientCode) throws Exception {
        List<SpamInfo> list = mapper.selectSpamInfoClientCode(clientCode);
        Optional<SpamInfo> spamInfo = list.stream().filter(p -> p.getClientCode().equals(clientCode)).findFirst();
        return spamInfo.orElse(null);
    }

    public List<SpamInfo> findByClientCode(List<Long> clientCodes) throws Exception {
        List<SpamInfo> list = new ArrayList<>();
        for(Long clientCode : clientCodes){
            List<SpamInfo> tempList = mapper.selectSpamInfoClientCode(clientCode);
            list.addAll(tempList);
        }
        return list;
    }

    public SpamInfo findBySeqClientCode(Long clientCode, Long seq) throws Exception {
        return mapper.selectSpamInfoClientCodeSeq(clientCode, seq);
    }

    @Deprecated
    public SpamInfo add(SpamInfo spamInfo){
        //
        return spamInfo;
    }

    @Deprecated
    public SpamInfo update(SpamInfo spamInfo){
        //
        return spamInfo;
    }
    @Deprecated
    public void delete(SpamInfo spamInfo){
        //
    }
}
