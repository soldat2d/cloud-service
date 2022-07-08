package ru.netology.diplom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.diplom.Repository.RepositoryMain;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class RepositoryMainTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RepositoryMain bookRepository;

	@Test
	public void testCustomNativeQuery() {
//		assertEquals(1, bookRepository.findAll().size());
//
//		assertNotNull(dataSource);
//		assertNotNull(entityManager);
	}
}