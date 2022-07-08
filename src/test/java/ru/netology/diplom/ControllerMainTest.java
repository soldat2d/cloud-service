package ru.netology.diplom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import ru.netology.diplom.Repository.RepositoryMain;
import ru.netology.diplom.Repository.User;

import java.nio.charset.Charset;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//public class ControllerMainTest {
//
//	ControllerMain controllerMain;
//
//	@Test
//	void loginNullParameterTest() {
//		assertThrows(NullPointerException.class, () -> controllerMain.login(null));
//	}
//
//}
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:test.properties")
//@AutoConfigureMockMvc(secure = false)
//@Retention(RetentionPolicy.RUNTIME)

@WebMvcTest(ControllerMain.class)
class ControllerMainTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepositoryMain repositoryMain;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void shouldReturnAuthToken() throws Exception {
		User user = new User();
		user.setLogin("user1@mail.com");
		user.setPassword("123");
		String uuid = UUID.randomUUID().toString();
		when(repositoryMain.login(user)).thenReturn(uuid);

		this.mockMvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
				.content(user))
				.andExpect(status().isOk());
//		(ResultMatcher) jsonPath("auth-token", Matchers.is(uuid))
	}
}
