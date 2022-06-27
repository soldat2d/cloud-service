package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    final private MainRepository repository;

    public MainService(@Autowired MainRepository repository) {
        this.repository = repository;
    }

    public String login(User user) {
        return repository.login(user);
    }

    public boolean logout(String authToken) {
        return repository.logout(authToken);
    }

}
