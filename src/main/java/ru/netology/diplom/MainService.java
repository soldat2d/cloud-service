package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    MainRepository repository;

    public boolean findUser(User user) {
        if (repository.findUser(user.getEmail()) != null) {
            return true;
        }
        return false;
    }
}
