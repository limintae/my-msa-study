package awaai.eureka.client.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestRestController {

    private final FeignTestClient feignTestClient;

    @GetMapping("/get")
    public boolean getTest() {
        boolean test = feignTestClient.getTest();
        return true;
    }

}
