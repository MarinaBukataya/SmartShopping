package com.smartshopping.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshopping.beans.GroceryList;
import com.smartshopping.beans.Item;
import com.smartshopping.beans.LoginResponse;
import com.smartshopping.security.LoginManager;
import com.smartshopping.security.TokensManager;
import com.smartshopping.security.UserType;
import com.smartshopping.services.ConsumerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("consumer")
@CrossOrigin(origins = "https://smartshopping-2e420.web.app", allowedHeaders = "*", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
//@CrossOrigin(origins = "https://smart-shopping-311716.ew.r.appspot.com", allowedHeaders = "*", allowCredentials = "true")
public class ConsumerController extends UserController {

	private final ConsumerService consumerService;
	private final LoginManager loginManager;
	private final TokensManager tokensManager;

	@Override
	@PostMapping("login/{name}/{password}")
	ResponseEntity<?> login(@PathVariable String name, @PathVariable String password) {
		try {
			String token = loginManager.login(name, password, UserType.CONSUMER);
			consumerService.setConsumerId(consumerService.getConsumerIdFromDB(name, password));
			return new ResponseEntity<>(new LoginResponse(token), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Name or password is incorrect",
					HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("currentmonth-grocerylists")
	public ResponseEntity<?> getCurrenMonthGroceryLists() {
		return new ResponseEntity<>(consumerService.getCurrentMonthGroceryLists(), HttpStatus.OK);
	}

	@GetMapping("grocerylists/{id}")
	public ResponseEntity<?> getOneGroceryList(@PathVariable(name = "id") int groceryListId) {
		return new ResponseEntity<>(consumerService.getOneGroceryList(groceryListId), HttpStatus.OK);
	}

	@PutMapping("grocerylists")
	public ResponseEntity<?> updateGroceryList(@RequestBody GroceryList groceryList) {
		return new ResponseEntity<>(consumerService.updateGroceryList(groceryList), HttpStatus.OK);
	}

	@PutMapping("items")
	public ResponseEntity<?> updateItem(@RequestBody Item item) {
		return new ResponseEntity<>(consumerService.updateItem(item), HttpStatus.OK);
	}

	@DeleteMapping("logout/{token}")
	public ResponseEntity<?> logout(@PathVariable String token) {
		tokensManager.deleteToken(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
