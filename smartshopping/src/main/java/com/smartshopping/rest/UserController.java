package com.smartshopping.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class UserController {

	abstract ResponseEntity<?> login(String name, String password);
	
	
}
