package com.sunny.ratelimiter.junits;

import com.sunny.ratelimiter.interceptor.RateLimitInterceptor;
import com.sunny.ratelimiter.strategy.Context;
import com.sunny.ratelimiter.strategy.RateLimitAssigner;
import com.sunny.ratelimiter.strategy.RateLimitStrategy;
import com.sunny.ratelimiter.strategy.TokenBucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class RateLimiterInterceptorTest {

    @InjectMocks
    private RateLimitAssigner rateLimitAssigner;

    @Mock
    RateLimitStrategy rateLimitStrategy;


    MockHttpServletRequest mockHttpServletRequest;

    @Mock
    TokenBucket tokenBucket;

    @Mock
    Map<String, RateLimitStrategy> chooseStrategy;


    @Mock
    private Context context;

    @InjectMocks
    RateLimitInterceptor rateLimitInterceptor;


    @BeforeEach
    public void intialize() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("user-id", "1");


    }


    @Test
    public void preHandleWithOutURI(@Mock HttpServletResponse response, @Mock Object handler)
            throws Exception {
        String apiKey = mockHttpServletRequest.getHeader("user-id");
        Mockito.when(chooseStrategy.containsKey(Mockito.anyString())).thenReturn(false);
       // Mockito.when(tokenBucket.resolveBucket(Mockito.any(), Mockito.any())).thenReturn(true);
        boolean actual = rateLimitAssigner.assignTokenWithRateLimitCapacity(Mockito.anyString(), response);
        assertEquals(false, actual);

        ;

    }

    @Test
    public void preHandleHeaderNull(@Mock HttpServletRequest request, @Mock HttpServletResponse response, @Mock Object handler)
            throws Exception {

        mockHttpServletRequest.removeHeader("user-id");
        boolean actual = rateLimitInterceptor.preHandle(mockHttpServletRequest, response, handler);

        assertEquals(false, actual);


    }

    @Test
    public void preHandleWithURI(@Mock HttpServletResponse response, @Mock Object handler)
            throws Exception {


        String apiKey = mockHttpServletRequest.getHeader("user-id");
        Mockito.when(chooseStrategy.get(Mockito.any())).thenReturn(tokenBucket);
        Mockito.when(chooseStrategy.containsKey(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenBucket.isRequestThrottledOrServed(Mockito.any(), Mockito.any())).thenReturn(true);
        boolean actual = rateLimitAssigner.assignTokenWithRateLimitCapacity(apiKey + "/api/v1/developers", response);
        assertEquals(true, actual);

    }
}
