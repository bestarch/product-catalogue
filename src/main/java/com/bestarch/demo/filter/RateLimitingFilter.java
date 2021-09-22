package com.bestarch.demo.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.bestarch.demo.util.RateLimitSessionCallback;

@Order(2)
@Component
public class RateLimitingFilter implements Filter, ErrorHandler {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${api.max.requests.minute}")
	private Integer API_MAX_REQUESTS_MINUTE;

	private static String ERR_MSG = "Request limit reached for API key: %s. Try after sometime";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String apiKey = req.getHeader("API-KEY");
		String clientKey = apiKey + ":" + LocalDateTime.now().getMinute();
		String permit = (String) redisTemplate.opsForValue().get(clientKey);
		if (ObjectUtils.isEmpty(permit) || (Integer.valueOf(permit) < API_MAX_REQUESTS_MINUTE)) {
			handleRequest(clientKey);
		} else {
			handle(resp, apiKey, 429, ERR_MSG);
			return;
		}
		chain.doFilter(request, response);
	}

	private void handleRequest(String clientKey) {
		redisTemplate.execute(RateLimitSessionCallback.builder().clientKey(clientKey).build());
	}
}
