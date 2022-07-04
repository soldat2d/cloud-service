package ru.netology.diplom;

import lombok.NonNull;
import lombok.experimental.StandardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.netology.diplom.Repository.*;

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
	public ResponseEntity<HttpStatus> logout(@CookieValue(value = "auth-token") @NonNull String authToken) {
		repository.logout(authToken);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<File>> list(@RequestParam(name = "limit", defaultValue = "3") Integer limit) {
		return new ResponseEntity<>(repository.list(limit), HttpStatus.OK);
	}

	@PostMapping("/file")
	public ResponseEntity<?> saveFile(MultipartRequest request) throws IOException {
		MultipartFile multipartFile = request.getFile("file");
		return repository.saveFile(multipartFile) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/file")
	public ResponseEntity<?> updateFile(@RequestParam(name = "filename") String fileNameOld, @RequestBody NewFileName filename) throws IOException {
		repository.updateFile(fileNameOld, filename.getFilename());
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/file")
	public ResponseEntity<byte[]> loadFile(@RequestParam(name = "filename") String filename) throws FileNotFoundException {
		return new ResponseEntity<>(repository.getFile(filename), HttpStatus.OK);
	}

	@DeleteMapping("/file")
	public ResponseEntity<?> deleteFile(@RequestParam(name = "filename") String fileName) {
		return repository.deleteFile(fileName) ? ResponseEntity.ok(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
