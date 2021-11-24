package com.fortunebill.springboot.healthIndicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Kavin
 * @date 2021年11月24日 22:30
 */
@Component
public class MyAppHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // 自定义的检查方法
        // Health.up().build()表示健康
        // Health.down().build()
        return Health.down().withDetail("msg", "服务异常").build();
    }

}
