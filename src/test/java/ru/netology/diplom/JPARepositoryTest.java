package ru.netology.diplom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.diplom.repository.FileDataRepository;
import ru.netology.diplom.repository.FileRepository;
import ru.netology.diplom.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPARepositoryTest {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileDataRepository fileDataRepository;

	@Autowired
	private UserRepository userRepository;

	@Container
	static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("test")
			.withPassword("test")
			.withDatabaseName("public");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
	}

	@Test
	public void userRepositoryTest() {
		assertEquals(2, userRepository.findByLoginAndPassword("user2@mail.com", "1234").get().getId());
	}

	@Test
	public void fileRepositoryTest() {
		assertEquals(4, fileRepository.findFirstByFilename("dockerd-rootless.sh").get().getId());
	}

	@Test
	public void fileDataRepositoryTest() {
		assertEquals(5, fileDataRepository.getReferenceById(2L).getData().length);
	}
}