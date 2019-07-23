package hello.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import hello.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConusmerServiceImpl implements ConsumerService {

    @Value("${app.service.url}")
    private String appServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "backupCall")
    public String call(String name) {
        ResponseEntity resultResponseEntity = restTemplate.postForEntity(appServiceUrl + "hello?name=" + name, null, String.class);
        if (resultResponseEntity != null && resultResponseEntity.getBody() != null) {
            return name + " says: " + resultResponseEntity.getBody().toString();
        }
        return null;
    }

    public String backupCall(String name) {
        return "Hi, I'm Hystix.";
    }
}
