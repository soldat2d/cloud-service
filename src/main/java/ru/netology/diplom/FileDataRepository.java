package ru.netology.diplom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<File, Long> {

}
