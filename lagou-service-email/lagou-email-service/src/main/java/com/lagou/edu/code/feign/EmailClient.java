package com.lagou.edu.code.feign;

import com.lagou.edu.api.email.feign.IEmailClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(IEmailClient.BASE_PATH)
public class EmailClient implements IEmailClient {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public boolean email(String email, String code) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(emailFrom);
        //邮件接收人
        message.setTo(email);
        //邮件主题
        message.setSubject("验证码");
        //邮件内容
        message.setText("登录验证码：" + code);
        //发送邮件
        mailSender.send(message);
        return true;
    }

}
