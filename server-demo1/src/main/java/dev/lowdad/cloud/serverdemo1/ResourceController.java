package dev.lowdad.cloud.serverdemo1;

import dev.lowdad.cloud.common.model.vo.UserInfoVO;
import dev.lowdad.cloud.serverdemo2.client.Demo2ResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final Demo2ResourceClient demo2ResourceClient;

    @Autowired
    public ResourceController(Demo2ResourceClient demo2ResourceClient) {
        this.demo2ResourceClient = demo2ResourceClient;
    }

    @GetMapping("/resource")
    public String resource(@AuthenticationPrincipal UserInfoVO userInfoVO) {
        System.out.println(userInfoVO.getUsername());
        return demo2ResourceClient.resource();
    }
}
