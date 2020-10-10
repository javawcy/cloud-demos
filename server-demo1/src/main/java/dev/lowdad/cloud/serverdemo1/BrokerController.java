package dev.lowdad.cloud.serverdemo1;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/29
 */
@RestController
public class BrokerController {

    @HystrixCommand(fallbackMethod = "defaultBroken")
    @GetMapping("/test")
    public String test() {
        return "success";
    }

    public String defaultBroken() {
        return "busy";
    }
}
