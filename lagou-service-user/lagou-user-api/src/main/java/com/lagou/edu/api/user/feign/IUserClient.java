package com.lagou.edu.api.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "lagou-user-service", path = IUserClient.BASE_PATH)
public interface IUserClient {

    String BASE_PATH = "/user";

    /**
     * 注册接⼝，true成功，false失败
     *
     * @param code
     */
    @GetMapping("/user/register/{email}/{password}/{code}")
    boolean register(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("code") String code);

    /**
     * 是否已注册，根据邮箱判断
     *
     * @return true代表已经注册过    false代表尚未注册
     */
    @GetMapping("/user/isRegistered/{email}")
    boolean isRegistered(@PathVariable("email") String email);

    /**
     * 登录接⼝，验证⽤户名密码合法性，根据⽤户名和
     * 密码⽣成token，token存⼊数据库，并写⼊cookie
     * 中，登录成功返回邮箱地址，重定向到欢迎⻚
     *
     * @param password
     * @return
     */
    @GetMapping("/user/login/{email}/{password}")
    String login(@PathVariable("email") String email, @PathVariable("password") String password);

    /**
     * 根据token查询⽤户登录邮箱接⼝
     *
     * @return 邮箱地址
     */
    @GetMapping("/user/info/{token}")
    String info(@PathVariable("token") String token);

}
