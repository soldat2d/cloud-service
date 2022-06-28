package ru.netology.diplom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByLoginAndPassword(String login, String password);
}
