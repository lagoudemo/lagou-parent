package com.lagou.edu.code.service.impl;

import com.lagou.edu.code.dao.AuthCodeDao;
import com.lagou.edu.code.entity.AuthCode;
import com.lagou.edu.code.service.IAuthCodeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthCodeServiceImpl implements IAuthCodeService {

    private final AuthCodeDao codeDao;

    @Override
    public String create(String email) {
        String code = RandomStringUtils.randomNumeric(6);
        AuthCode entity = new AuthCode();
        entity.setEmail(email);
        entity.setCode(code);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        // 30秒超时
        entity.setExpireTime(now.plusSeconds(30));
        codeDao.save(entity);
        return code;
    }

    @Override
    public int validate(String email, String code) {
        AuthCode authCode = codeDao.findFirstByEmailOrderByIdDesc(email);
        // 邮箱/验证码错误
        if (Objects.isNull(authCode) || !Objects.equals(authCode.getCode(), code)) {
            return 1;
        }

        // 超时
        if (authCode.getExpireTime().isBefore(LocalDateTime.now())) {
            return 2;
        }

        // 正确
        return 0;
    }

}
