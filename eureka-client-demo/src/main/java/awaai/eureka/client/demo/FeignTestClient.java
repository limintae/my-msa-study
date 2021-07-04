package awaai.eureka.client.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="service-2", url = "http://localhost:8085")
public interface FeignTestClient {

    @RequestMapping(method = RequestMethod.GET, value="/test2/get")
    boolean getTest();

}
