package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MainRepository {
    final private Map<String, User> authorizedUsers;
//    @Autowired
    final private UserRepository userRepository;
    final private FileRepository fileRepository;

    public MainRepository(@Autowired UserRepository userRepository, @Autowired FileRepository fileRepository) {
        this.authorizedUsers = new HashMap<>();
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public String login(User user) {
        user = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
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

    public boolean file(File file) {
        return fileRepository.save(file) != null;
    }

    public List<File> list () {
        Page<File> temp = fileRepository.findSome(PageRequest.of(0, 3));
        return temp.toList();
    }
}
