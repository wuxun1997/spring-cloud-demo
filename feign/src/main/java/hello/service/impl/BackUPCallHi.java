package hello.service.impl;

import hello.service.ConsumerService;
import org.springframework.stereotype.Component;

@Component
public class BackUPCallHi implements ConsumerService {

    @Override
    public String hello(String name) {
        return "I'm feign hystrix.";
    }
}
