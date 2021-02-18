package com.lagou.edu.code.service;


public interface IAuthCodeService {

    /**
     * 生成验证码并保存
     *
     * @param email 邮箱
     * @return 验证码
     */
    String create(String email);

    /**
     * 校验验证码是否正确
     *
     * @param email 邮箱
     * @param code 验证码
     * @return 0正确1错误2超时
     */
    int validate(String email, String code);

}
