package com.smartshopping.security;

import com.smartshopping.services.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserData {
	
	private UserService userService;
	private long timeStamp;
	
}
