package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<List<File>> list(@RequestParam(name = "limit", defaultValue = "4") Integer limit, HttpServletRequest request) {
        return new ResponseEntity<>(service.list(limit), HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<?> saveFile(MultipartRequest request) throws IOException {
        return service.file(request) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFile(MultipartRequest request) throws IOException {
        return service.file(request) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/file")
    public ResponseEntity<?> loadFile(MultipartRequest request) throws IOException {
        return service.file(request) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam(name = "filename") String filename) {
        return service.deleteFile(filename) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
