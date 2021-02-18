package com.lagou.edu.api.email.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "lagou-email-service", path = IEmailClient.BASE_PATH)
public interface IEmailClient {

    String BASE_PATH = "/email";

    /**
     * 发送验证码到邮箱
     *
     * @param email
     * @param code
     * @return true成功，false失败
     */
    @GetMapping("/email/{email}/{code}")
    boolean email(@PathVariable("email") String email, @PathVariable("code") String code);

}
