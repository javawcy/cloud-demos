package dev.lowdad.cloud.serverdemo2.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试rest
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/28
 */
@RestController
public class TestController {

    @GetMapping("/resource")
    public String resource() {
        return "resource2";
    }
}
