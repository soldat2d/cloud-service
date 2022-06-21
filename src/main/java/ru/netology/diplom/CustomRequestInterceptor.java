package ru.netology.diplom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
public class CustomRequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        long startTime = Instant.now().toEpochMilli();
        logger.info(request.getMethod() + " " + request.getRequestURL() + " " + request.getQueryString());
        request.setAttribute("startTime", startTime);
        return true;
    }

}