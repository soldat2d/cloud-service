package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class MainRepository {
    final private Map<String, User> authorizedUsers;
//    @Autowired
    final private CustomRepository repository;

    public MainRepository(@Autowired CustomRepository repository) {
        this.authorizedUsers = new HashMap<>();
        this.repository = repository;
    }

    public String login(User user) {
        user = repository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (user!=null) {
            String uuid = UUID.randomUUID().toString();
            authorizedUsers.put(uuid, user);
            return uuid;
        }
        return null;
    }

    public boolean logout (String authToken) {
        return authorizedUsers.remove(authToken) != null;
    }

    public boolean isAuthorized (String authToken) {
        return authorizedUsers.containsKey(authToken);
    }
}
