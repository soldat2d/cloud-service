package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/")
public class MainController implements WebMvcConfigurer {

    @Autowired
    CustomRequestInterceptor customRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userCredentials) {
        System.out.println(userCredentials);
        return new ResponseEntity<>("{\"auth-token\":\"" + UUID.randomUUID() + "\"}", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "auth-token", defaultValue = "none") String authToken) {
        if (authToken.equals("none")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(name = "limit", defaultValue = "3") Integer limit, HttpServletRequest request) {
//        PageRequest
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<?> file(@CookieValue(value = "auth-token", defaultValue = "none") String authToken) {
        if (authToken.equals("none")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
