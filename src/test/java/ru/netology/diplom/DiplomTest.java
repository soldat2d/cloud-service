package ru.netology.diplom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = AlternativeJUnit5ApplicationTest.Initializer.class)
//class AlternativeJUnit5ApplicationTest {
//
//	@Container
//	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
//			.withPassword("inmemory")
//			.withUsername("inmemory");
//
//	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//		@Override
//		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//			TestPropertyValues values = TestPropertyValues.of(
//					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
//					"spring.datasource.password=" + postgreSQLContainer.getPassword(),
//					"spring.datasource.username=" + postgreSQLContainer.getUsername()
//			);
//			values.applyTo(configurableApplicationContext);
//		}
//	}
//
//	@Test
//	void contextLoads() {
//	}
//}
@WebMvcTest(ControllerMain.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiplomTest {
	@Autowired
	TestRestTemplate restTemplate;

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
			.withPassword("inmemory")
			.withUsername("inmemory");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
	}

	@Test
	void contextLoads() {
	}
//	static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:12")
//			.withUsername("duke")
//			.withPassword("secret")
//			.withInitScript("config/INIT.sql")
//			.withDatabaseName("tescontainers");
//
//	@Test
//	void testPostgreSQLModule() {
//		System.out.println(database.getJdbcUrl());
//		System.out.println(database.getTestQueryString());
//
//		// jdbc:postgresql://localhost:32827/tescontainers?loggerLevel=OFF
//		// SELECT 1
//	}

//	public static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest").withExposedPorts(8080);
//	public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);
//
//	@BeforeAll
//	public static void setUp() {
//		devApp.start();
//		prodApp.start();
//	}
//
	@Test
	void devTestEntity() {
//		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
//		Assertions.assertEquals("Current profile is dev", forEntity.getBody());
	}

}

//@ExtendWith(MockitoExtension.class)
//class PricingServiceTest {
//
//	@Mock // Instruct Mockito to mock this object
//	private ProductVerifier mockedProductVerifier;
//
//	@Test
//	void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
//		//Specify what boolean value to return for this test
//		when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods")).thenReturn(true);
//
//		PricingService classUnderTest = new PricingService(mockedProductVerifier);
//
//		assertEquals(new BigDecimal("99.99"), classUnderTest.calculatePrice("AirPods"));
//	}
//}
//@ExtendWith(MockitoExtension.class)
//class PricingServiceTest {
//
//	// ... rest like above
//
//	@Mock
//	private ProductReporter mockedProductReporter;
//
//	@Test
//	void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
//		when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods")).thenReturn(true);
//
//		PricingService cut = new PricingService(mockedProductVerifier, mockedProductReporter);
//
//		assertEquals(new BigDecimal("99.99"), cut.calculatePrice("AirPods"));
//
//		//verify the interaction
//		verify(mockedProductReporter).notify("AirPods");
//	}
//}

//	@LocalServerPort
//	private Integer port;

//@Mock, @Spy, @Captor, when(), verify(),
//		thenReturn(), ArgumentMatcher