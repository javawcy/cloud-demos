package dev.lowdad.cloud.serverdemo2.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *
 * </P>
 *
 * @author Chongyu
 * @since 2020/9/28
 */
@FeignClient(name = "server-demo2")
public class ResourceClient {


}
