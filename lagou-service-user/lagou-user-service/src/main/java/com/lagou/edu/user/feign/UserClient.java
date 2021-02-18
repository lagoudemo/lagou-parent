package com.lagou.edu.user.feign;

import com.lagou.edu.api.code.feign.ICodeClient;
import com.lagou.edu.api.user.constant.AuthConstant;
import com.lagou.edu.api.user.feign.IUserClient;
import com.lagou.edu.user.dao.TokenDao;
import com.lagou.edu.user.dao.UserDao;
import com.lagou.edu.user.entity.Token;
import com.lagou.edu.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(IUserClient.BASE_PATH)
public class UserClient implements IUserClient {

    private final UserDao userDao;
    private final TokenDao tokenDao;
    private final ICodeClient codeClient;

    @Override
    public boolean register(String email, String password, String code) {
        int validate = codeClient.validate(email, code);
        if (validate == 1 || validate == 2) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userDao.save(user);
        return true;
    }

    @Override
    public boolean isRegistered(String email) {
        User user = userDao.findByEmail(email);
        return Objects.nonNull(user);
    }

    /**
     * 登录接⼝，验证⽤户名密码合法性，根据⽤户名和密码⽣成token，
     * token存⼊数据库，并写⼊cookie中，登录成功返回邮箱地址，重定向到欢迎⻚
     *
     * @param password
     * @return
     */
    @Override
    public String login(String email, String password) {
        User user = userDao.findByEmail(email);
        if (Objects.isNull(user) || !Objects.equals(password, user.getPassword())) {
            return null;
        }

        // 新增token记录
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setEmail(email);
        token.setToken(tokenValue);
        tokenDao.save(token);

        // 设置cookie

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        Cookie cookie = new Cookie(AuthConstant.TOKEN_COOKIE_NAME, tokenValue);
        response.addCookie(cookie);

        return user.getEmail();
    }

    @Override
    public String info(String token) {
        Token tokenEntity = tokenDao.findByToken(token);
        if (Objects.nonNull(tokenEntity)) {
            return tokenEntity.getToken();
        }
        return null;
    }
}
