package dev.lowdad.cloud.serverdemo2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/28
 */
@FeignClient(name = "server-demo2")
public interface ResourceClient {

    @GetMapping("/demo2/resource")
    String resource();
}
