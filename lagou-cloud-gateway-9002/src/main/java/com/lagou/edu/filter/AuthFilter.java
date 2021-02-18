package com.lagou.edu.filter;

import com.lagou.edu.api.user.constant.AuthConstant;
import com.lagou.edu.api.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private IUserClient userClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        List<HttpCookie> httpCookies = cookies.get(AuthConstant.TOKEN_COOKIE_NAME);
        Optional<HttpCookie> cookie = httpCookies.stream().filter(item ->
                Objects.equals(item.getName(), AuthConstant.TOKEN_COOKIE_NAME) && !StringUtils.isEmpty(item.getValue()))
                .findFirst();

        String email = null;
        if (cookie.isPresent()) {
            email = userClient.info(cookie.get().getValue());
        }

        if (StringUtils.isEmpty(email)) {
            // 拒绝访问，返回
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
            String data = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
