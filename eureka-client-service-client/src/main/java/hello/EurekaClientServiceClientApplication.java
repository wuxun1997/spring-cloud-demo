package hello;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import hello.config.SayHeyConfiguration;
import hello.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RibbonClient(name = "a-bootiful-client", configuration = SayHeyConfiguration.class)
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientServiceClientApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public IRule ribbonRule() {
//        return new AvailabilityFilteringRule();
//    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientServiceClientApplication.class, args);
    }
}


@RestController
class ServiceInstanceRestController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "Spring Cloud") String name) {

        return consumerService.call(name);
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}
