package ru.netology.diplom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
