package com.logan.ndp.handler.flowcontrol.annotations;

import com.logan.ndp.handler.enums.RateLimitStrategy;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 单机限流注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface LocalRateLimit {
    RateLimitStrategy rateLimitStrategy() default RateLimitStrategy.REQUEST_RATE_LIMIT;
}
