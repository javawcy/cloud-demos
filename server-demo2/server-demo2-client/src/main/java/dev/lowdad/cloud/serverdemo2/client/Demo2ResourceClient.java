package dev.lowdad.cloud.serverdemo2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/28
 */
@FeignClient("service-demo2")
public interface Demo2ResourceClient {

    @GetMapping("/demo2/resource")
    @ResponseBody
    String resource();
}