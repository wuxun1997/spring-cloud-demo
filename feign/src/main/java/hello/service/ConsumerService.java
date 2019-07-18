package hello.service;

import hello.service.impl.BackUPCallHi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "a-bootiful-client", fallback = BackUPCallHi.class)
public interface ConsumerService {
    @RequestMapping("/hello")
    String hello(@RequestParam(value = "name") String name);
}
