package com.smartshopping.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.smartshopping.services.AdminService;
import com.smartshopping.services.ConsumerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Lazy
public class LoginManager {

	private final ApplicationContext ctx;
	private final TokensManager tokensManager;
	private ConsumerService consumerService;
	private AdminService adminService;

	public String login(String name, String password, UserType userType) {
		switch (userType) {
		case ADMINISTRATOR:
			adminService = ctx.getBean(AdminService.class);
			if (adminService.login(name, password)) {
				adminService.setAdminId(adminService.getAdminIdFromDB(name, password));
				return tokensManager.addToken(adminService);
			}
			break;
		case CONSUMER:
			consumerService = ctx.getBean(ConsumerService.class);
			if (consumerService.login(name, password)) {
				consumerService.setConsumerId(consumerService.getConsumerIdFromDB(name, password));
				return tokensManager.addToken(consumerService);
			}
			break;
		default:
			return "Not Found";
		}
		return null;
	}
}
