package com.lagou.edu.api.code.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "lagou-code-service", path = ICodeClient.BASE_PATH)
public interface ICodeClient {
    String BASE_PATH = "/code";

    /**
     * ⽣成验证码并发送到对应邮箱
     *
     * @param email 邮箱
     * @return 成功true，失败false
     */
    @GetMapping("/code/create/{email}")
    boolean create(@PathVariable("email") String email);

    /**
     * 校验验证码是否正确
     *
     * @param email 邮箱
     * @param code  验证码
     * @return 0正确1错误2超时
     */
    @GetMapping("/code/validate/{email}/{code}")
    int validate(@PathVariable("email") String email, @PathVariable("code") String code);

}
