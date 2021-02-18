package com.lagou.edu.filter;

import com.lagou.edu.helper.IpRequestWindow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class IpFilter implements GlobalFilter, Ordered {

    @Value("${ip-filter.window-minutes}")
    private int windowsMinutes;

    @Value("${ip-filter.max-request-times}")
    private int maxRequestTimes;

    private static final String filterPath = "/register";

    private static final Map<String, IpRequestWindow> requestWindowMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        RequestPath path = request.getPath();

        if (!Objects.equals(filterPath, path.value())) {
            return chain.filter(exchange);
        }

        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();

        // 获取ip请求信息
        IpRequestWindow requestWindow = requestWindowMap.getOrDefault(clientIp, new IpRequestWindow(1, LocalDateTime.now()));
        if (requestWindow.isExpire(windowsMinutes)) {
            requestWindow.resetWindow();
        }

        if (requestWindow.shouldLimit(maxRequestTimes)) {
            // 拒绝访问，返回
            response.setStatusCode(HttpStatus.FORBIDDEN); // 状态码
            log.debug("=====>IP: {}, 最近{}分钟访问次数{}次超过限制次数{}次", clientIp, windowsMinutes, requestWindow.getRequestCount(), maxRequestTimes);
            String data = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
