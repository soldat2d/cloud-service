package ru.netology.diplom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.netology.diplom.Repository.AuthToken;
import ru.netology.diplom.Repository.File;
import ru.netology.diplom.Repository.RepositoryMain;
import ru.netology.diplom.Repository.User;

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

@WebMvcTest(ControllerMain.class)
class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepositoryMain repository;

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
		when(repository.login(any(User.class))).thenReturn(uuid);

		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());

		user.setLogin("");
		user.setPassword("");
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)));

		user.setLogin("test@mail.com");
		user.setPassword("qwe");
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				                     .content(mapper.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(new AuthToken(uuid))));
	}

	@Test
	public void logoutTest() throws Exception {
		when(repository.logout(anyString())).thenReturn(true);
		this.mockMvc.perform(post("/logout").cookie(new Cookie("auth-token", "")))
				.andExpect(status().isBadRequest());
		this.mockMvc.perform(post("/logout").cookie(new Cookie("auth-token", "23swdf23e42342")))
				.andExpect(status().isOk());
	}

	@Test
	public void listTest() throws Exception {
		when(repository.list(anyInt())).thenReturn(List.of());
		this.mockMvc.perform(get("/list"))
				.andExpect(status().isOk());
		this.mockMvc.perform(get("/list").param("limit", "yyt"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void fileTest() throws Exception {
		File file = new File();
		ObjectMapper mapper = new ObjectMapper();

		when(repository.saveFile(any())).thenReturn(true);
		when(repository.updateFile(anyString(), anyString())).thenReturn(true);
		when(repository.getFile(anyString())).thenReturn("Mini file".getBytes());
		when(repository.deleteFile(anyString())).thenReturn(true);

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