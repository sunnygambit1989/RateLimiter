package com.sunny.ratelimiter.strategy;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import com.sunny.ratelimiter.util.AppConstant;

/*
It selects the  rate limitation algorithms based on the provided strategy.
 */
@Component
public class RateLimitAssigner {

    Map<String, com.blue.optima.ratelimiter.strategy.RateLimitStrategy> chooseStrategy;

    @PostConstruct
    public void init() {
        chooseStrategy = new HashMap<>();
        chooseStrategy.put(AppConstant.TOKEN_BUCKET, new TokenBucket());

    }

    public boolean assignTokenWithRateLimitCapacity(String key, HttpServletResponse response) throws Exception {

        if (chooseStrategy.containsKey(AppConstant.TOKEN_BUCKET)) {
            Context context = getContext();
            return context.performOperation(key, response);
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error");
            return false;
        }
    }

    public Context getContext() {
        return new Context(chooseStrategy.get(AppConstant.TOKEN_BUCKET));
    }


}
