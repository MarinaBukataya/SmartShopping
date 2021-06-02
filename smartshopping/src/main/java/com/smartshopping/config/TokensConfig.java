package com.smartshopping.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartshopping.security.UserData;

@Configuration
public class TokensConfig {
	@Bean
	public Map<String, UserData> map() {
		return new HashMap<>();
	}
}
