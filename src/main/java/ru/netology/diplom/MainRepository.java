package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MainRepository {
    private Map<String, User> authorizedUsers;
    @Autowired
    CustomRepository repository;

    public User findUser (String email) {

        return repository.findByEmail(email);
    }
}
