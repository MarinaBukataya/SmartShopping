package com.smartshopping.security;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartshopping.services.UserService;

import javassist.NotFoundException;
import lombok.Data;

@Data
@Component
public class TokensManager {

	@Autowired
	private Map<String, UserData> tokens;

	public String addToken(UserService userService) {
		UserData userData = new UserData(userService, System.currentTimeMillis());
		String token = UUID.randomUUID().toString();
		tokens.put(token, userData);
		return token;
	}

	public boolean ifTokenExists(String token) throws NotFoundException {
		if (tokens.containsKey(token)) {
			return true;
		}
		throw new NotFoundException("Token was not recognised");
	}


	public void deleteToken(String token) {
		this.tokens.remove(token);
	}
	
}