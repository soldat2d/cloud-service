package ru.netology.diplom;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomRequestInterceptor implements HandlerInterceptor {

    final private MainRepository repository;

    public CustomRequestInterceptor(@Autowired MainRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        logger.info(request.getMethod() + " " + request.getRequestURL() + " " + request.getQueryString());
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth-token") && repository.isAuthorized(cookie.getValue())) {
                    logger.info("Request authorized");
                    return true;
                }
            }
        }
        response. setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.info("Unauthorized error");
//        change to false
        return true;
    }

}