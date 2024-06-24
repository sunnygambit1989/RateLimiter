package com.sunny.ratelimiter.strategy;

import javax.servlet.http.HttpServletResponse;


public class Context {

    RateLimitStrategy rateLimitStrategy;

    public Context(RateLimitStrategy rateLimitStrategy) {
        this.rateLimitStrategy = rateLimitStrategy;
    }

    public boolean performOperation(String key, HttpServletResponse response) throws Exception {
        return rateLimitStrategy.isRequestThrottledOrServed(key,response);
    }
}
