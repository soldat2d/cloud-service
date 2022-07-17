package ru.netology.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.diplom.repository.AuthToken;
import ru.netology.diplom.repository.MainRepository;
import ru.netology.diplom.repository.User;

@Service
public class AuthService {

	final private MainRepository repository;

	public AuthService(@Autowired MainRepository repository) {
		this.repository = repository;
	}

	public ResponseEntity<AuthToken> login(User user) {
		return new ResponseEntity<>(new AuthToken(repository.login(user)), HttpStatus.OK);
	}

	public ResponseEntity<Boolean> logout(String authToken) {
		return new ResponseEntity<>(repository.logout(authToken), HttpStatus.OK);
	}
}
