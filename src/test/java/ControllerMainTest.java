import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import ru.netology.diplom.ControllerMain;
import ru.netology.diplom.Repository.RepositoryMain;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
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
//@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
//@AutoConfigureMockMvc(secure = false)
//@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(ControllerMain.class)
class ShoppingCartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepositoryMain shoppingCartRepository;

	MockHttpServletRequestBuilder

	@Test
	public void shouldReturnAllShoppingCarts() throws Exception {
		when(shoppingCartRepository.findAll()).thenReturn(
				List.of(new ShoppingCart("42",
				                         List.of(new ShoppingCartItem(
						                         new Item("MacBook", 999.9), 2)
				                         ))));

		this.mockMvc.perform(get("/api/carts"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", Matchers.is("42")))
				.andExpect(jsonPath("$[0].cartItems.length()", Matchers.is(1)))
				.andExpect(jsonPath("$[0].cartItems[0].item.name", Matchers.is("MacBook")))
				.andExpect(jsonPath("$[0].cartItems[0].quantity", Matchers.is(2)));
	}
}
