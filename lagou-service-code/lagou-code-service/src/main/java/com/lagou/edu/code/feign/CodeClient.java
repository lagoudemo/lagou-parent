package com.lagou.edu.code.feign;

import com.lagou.edu.api.code.feign.ICodeClient;
import com.lagou.edu.api.email.feign.IEmailClient;
import com.lagou.edu.code.service.IAuthCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(ICodeClient.BASE_PATH)
public class CodeClient implements ICodeClient {

    private final IAuthCodeService codeService;
    private final IEmailClient emailClient;

    @Override
    public boolean create(String email) {
        // 生成验证码并保存
        String code = codeService.create(email);
        // 发送验证码
        return emailClient.email(email, code);
    }

    @Override
    public int validate(String email, String code) {
        return codeService.validate(email, code);
    }

}
