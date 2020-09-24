package dev.lowdad.cloud.serverdemo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/24
 */
@RestController
public class ResourceController {

    @GetMapping("/resource")
    public String resource() {
        return "resource";
    }
}
