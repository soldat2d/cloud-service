package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/")
public class MainController implements WebMvcConfigurer {

    final private MainService service;
    final private CustomRequestInterceptor customRequestInterceptor;

    public MainController(@Autowired MainService service, @Autowired CustomRequestInterceptor customRequestInterceptor) {
        this.service = service;
        this.customRequestInterceptor = customRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
//        System.out.println(user);
        String uuid = service.login(user);
        if (uuid != null) {
            return new ResponseEntity<>("{\"auth-token\":\"" + uuid + "\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error. No such user account", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "auth-token", defaultValue = "none") String authToken) {
        if (!authToken.equals("none") && service.logout(authToken)) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(name = "limit", defaultValue = "3") Integer limit, HttpServletRequest request) {
//        PageRequest
        System.out.println("LISTING HERE");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<?> file(@RequestBody String file) {
        System.out.println(file.length());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
