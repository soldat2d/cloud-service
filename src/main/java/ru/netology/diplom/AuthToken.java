package ru.netology.diplom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthToken {
	@JsonProperty("auth-token")
	private String authToken;
}
