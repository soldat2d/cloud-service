package ru.netology.diplom;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.netology.diplom.Repository.File;
import ru.netology.diplom.Repository.Marker.ListFile;
import ru.netology.diplom.Repository.Marker.UpdateName;
import ru.netology.diplom.Repository.RepositoryMain;
import ru.netology.diplom.Repository.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/")
public class ControllerMain implements WebMvcConfigurer {

    final private RepositoryMain repository;
    final private CustomRequestInterceptor customRequestInterceptor;

    public ControllerMain(@Autowired RepositoryMain repository, @Autowired CustomRequestInterceptor customRequestInterceptor) {
        this.repository = repository;
        this.customRequestInterceptor = customRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody @Validated User user) {
        return new ResponseEntity<>(new AuthToken(repository.login(user)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@CookieValue(value = "auth-token") @NotNull @NotBlank String authToken) {
        return new ResponseEntity<>(repository.logout(authToken), HttpStatus.OK);
    }

    @JsonView(ListFile.class)
    @GetMapping("/list")
    public ResponseEntity<List<File>> list(@RequestParam(name = "limit", defaultValue = "3") @NotNull @Min(1) @Max(9) Integer limit) {
        return new ResponseEntity<>(repository.list(limit), HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<Boolean> saveFile(@RequestPart @NotNull MultipartFile file) throws IOException {
//		MultipartFile multipartFile = file.getFile("file");
        return new ResponseEntity<>(repository.saveFile(file), HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<HttpStatus> updateFile(@RequestParam(name = "filename") @NotNull @NotBlank String fileNameOld,
                                                 @RequestBody @Validated(UpdateName.class) File fileNameNew) {
        return repository.updateFile(fileNameOld, fileNameNew.getFilename()) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> loadFile(@RequestParam(name = "filename") @NotNull @NotBlank String filename) throws FileNotFoundException {
        return new ResponseEntity<>(repository.getFile(filename), HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<Boolean> deleteFile(@RequestParam(name = "filename") @NotNull @NotBlank String fileName) throws FileNotFoundException {
        return new ResponseEntity<>(repository.deleteFile(fileName), HttpStatus.OK);
    }
}
