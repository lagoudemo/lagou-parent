package com.lagou.edu.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 请求窗口
 */
@Data
@AllArgsConstructor
public class IpRequestWindow {

    private Integer requestCount;

    private LocalDateTime createTime;

    /**
     * 是否时间窗口已过期
     *
     * @param windowsMinutes
     * @return
     */
    public boolean isExpire(int windowsMinutes) {
        return LocalDateTime.now().isAfter(createTime.plusMinutes(windowsMinutes));
    }

    /**
     * 重置时间窗口
     */
    public void resetWindow() {
        requestCount = 1;
        createTime = LocalDateTime.now();
    }

    /**
     * 是否需要限制
     *
     * @param maxRequestTimes
     * @return
     */
    public boolean shouldLimit(int maxRequestTimes) {
        return requestCount >= maxRequestTimes;
    }
}
