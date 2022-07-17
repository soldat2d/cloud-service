package ru.netology.diplom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.netology.diplom.config.CustomRequestInterceptor;
import ru.netology.diplom.controller.AuthController;
import ru.netology.diplom.controller.FileController;
import ru.netology.diplom.repository.AuthToken;
import ru.netology.diplom.repository.File;
import ru.netology.diplom.repository.User;
import ru.netology.diplom.service.AuthService;
import ru.netology.diplom.service.FileService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class, FileController.class})
class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private FileService fileService;

	@MockBean
	private CustomRequestInterceptor requestInterceptor;

	@BeforeEach
	public void requestAuthorizer() {
		when(requestInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class)))
				.thenReturn(true);
	}

	@Test
	public void loginTest() throws Exception {
		User user = new User();
		String uuid = UUID.randomUUID().toString();
		ObjectMapper mapper = new ObjectMapper();
		when(authService.login(any(User.class))).thenReturn(new ResponseEntity<>(new AuthToken(uuid), HttpStatus.OK));

		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());

		user.setLogin("");
		user.setPassword("");
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());

		user.setLogin("test@mail.com");
		user.setPassword("qwe");
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(new AuthToken(uuid))));
	}

	@Test
	public void logoutTest() throws Exception {
		when(authService.logout(anyString())).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
		this.mockMvc.perform(post("/logout").cookie(new Cookie("auth-token", "")))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post("/logout").cookie(new Cookie("auth-token", "23swdf23e42342")))
				.andExpect(status().isOk());
	}

	@Test
	public void listTest() throws Exception {
		when(fileService.list(anyInt())).thenReturn(new ResponseEntity<>(List.of(), HttpStatus.OK));
		this.mockMvc.perform(get("/list"))
				.andExpect(status().isOk());
		this.mockMvc.perform(get("/list").param("limit", "yyt"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void fileTest() throws Exception {
		File file = new File();
		ObjectMapper mapper = new ObjectMapper();

		when(fileService.saveFile(any())).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
		when(fileService.updateFile(anyString(), any(File.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		when(fileService.getFile(anyString())).thenReturn(new ResponseEntity<>("Mini file".getBytes(), HttpStatus.OK));
		when(fileService.deleteFile(anyString())).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

		this.mockMvc.perform(multipart("/file"))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(multipart("/file").file("file", "Mini file".getBytes()))
				.andExpect(status().isOk());

		this.mockMvc.perform(put("/file").param("filename", "qwe")
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(file)))
				.andExpect(status().isBadRequest());
		file.setFilename("asd");
		this.mockMvc.perform(put("/file").param("filename", "")
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(file)))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(put("/file").param("filename", "qwe")
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(file)))
				.andExpect(status().isOk());

		this.mockMvc.perform(get("/file").param("filename", ""))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(get("/file").param("filename", "qwe"))
				.andExpect(status().isOk());

		this.mockMvc.perform(delete("/file").param("filename", ""))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(delete("/file").param("filename", "qwe"))
				.andExpect(status().isOk());
	}
}