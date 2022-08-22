package ib.api.validate.controller;

import ib.api.validate.model.SpamInfo;
import ib.api.validate.service.SpamCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpamCheckController {
    private final SpamCheckService service;

    @GetMapping("/spam/{clientCode}")
    public SpamInfo findByClientCode(@PathVariable("clientCode") Long clientCode) throws Exception {
        return service.findByClientCode(clientCode);
    }

    @GetMapping("/spam/{clientCode}/{seq}")
    public SpamInfo findByIdWithAccounts(@PathVariable("clientCode") Long clientCode, @PathVariable("seq") Long seq) throws Exception {
        return service.findBySeqClientCode(clientCode, seq);
    }

    @GetMapping("/spam/clientCode")
    public List<SpamInfo> find(@RequestBody List<Long> clientCodeList) throws Exception {
        return service.findByClientCode(clientCodeList);
    }

    @DeleteMapping("/spam/{clientCode}/{seq}")
    public void delete(@PathVariable("clientCode") Long clientCode, @PathVariable("seq") Long seq) {
        service.delete(SpamInfo.builder()
                .clientCode(clientCode)
                .seq(seq)
                .build());
    }
}
