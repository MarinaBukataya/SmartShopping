package com.smartshopping.security;

import java.util.Calendar;
import java.util.Date;
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

	public void deleteExpiredTokens() {
		this.tokens.values().removeIf(x -> {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, 30);
			Date tokenCreate = new Date(x.getTimeStamp());
			return now.after(tokenCreate);
		});
	}

	public void deleteToken(String token) {
		this.tokens.remove(token);
	}
	
//	public void deleteExpiredTokens() {
//		this.tokens.values().removeIf(x -> {
//			Date creationTime = new Date(x.getTimeStamp());
//			Date expirationTime = new Date();
//			expirationTime.setTime(creationTime.getTime() + 30 * 60 * 1000);
//			return creationTime.after(expirationTime);
//		});
//	}

}