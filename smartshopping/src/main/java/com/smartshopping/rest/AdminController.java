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

import com.smartshopping.beans.Administrator;
import com.smartshopping.beans.Category;
import com.smartshopping.beans.Consumer;
import com.smartshopping.beans.GroceryList;
import com.smartshopping.beans.Item;
import com.smartshopping.beans.LoginResponse;
import com.smartshopping.exceptions.SignupException;
import com.smartshopping.security.LoginManager;
import com.smartshopping.security.TokensManager;
import com.smartshopping.security.UserType;
import com.smartshopping.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@CrossOrigin(origins = "https://smartshopping-2e420.web.app", allowedHeaders = "*", allowCredentials = "true")
//@CrossOrigin(origins = "https://smart-shopping-311716.ew.r.appspot.com", allowedHeaders = "*", allowCredentials = "true")
public class AdminController extends UserController {

	private final AdminService adminService;
	private final LoginManager loginManager;
	private final TokensManager tokensManager;

	@PostMapping("signup")
	public ResponseEntity<?> signup(@RequestBody Administrator administrator) {
		try {
			adminService.signup(administrator);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (SignupException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@PostMapping("login/{name}/{password}")
	public ResponseEntity<?> login(@PathVariable String name, @PathVariable String password) {
		try {
			String token = loginManager.login(name, password, UserType.ADMINISTRATOR);
			adminService.setAdminId(adminService.getAdminIdFromDB(name, password));
			return new ResponseEntity<>(new LoginResponse(token), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Name or password is incorrect",
					HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("consumers")
	public ResponseEntity<?> getAllConsumers() {
		return new ResponseEntity<>(adminService.getAllConsumers(), HttpStatus.OK);
	}

	@PostMapping("consumers")
	public ResponseEntity<?> addConsumer(@RequestBody Consumer consumer) {
		try {
			return new ResponseEntity<>(adminService.addConsumer(consumer), HttpStatus.CREATED);
		} catch (SignupException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("consumers")
	public ResponseEntity<?> updateConsumer(@RequestBody Consumer consumer) {
		return new ResponseEntity<>(adminService.updateConsumer(consumer), HttpStatus.OK);
	}

	@DeleteMapping("consumers/{id}")
	public ResponseEntity<?> deleteConsumer(@PathVariable(name = "id") int consumerId) {
		adminService.deleteConsumer(consumerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("grocerylists")
	public ResponseEntity<?> createGroceryList(@RequestBody GroceryList groceryList) {
		return new ResponseEntity<>(adminService.createGroceryList(groceryList), HttpStatus.CREATED);
	}

	@GetMapping("grocerylists/{id}")
	public ResponseEntity<?> getOneGroceryList(@PathVariable(name = "id") int groceryListId) {
		return new ResponseEntity<>(adminService.getOneGroceryList(groceryListId), HttpStatus.OK);
	}

	@PutMapping("grocerylists")
	public ResponseEntity<?> updateGroceryList(@RequestBody GroceryList groceryList) {
		return new ResponseEntity<>(adminService.updateGroceryList(groceryList), HttpStatus.OK);
	}

	@DeleteMapping("delete-grocerylist/{id}")
	public ResponseEntity<?> deleteGroceryList(@PathVariable(name = "id") int groceryListId) {
		adminService.deleteGroceryList(groceryListId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("currentmonth-grocerylists")
	public ResponseEntity<?> getCurrenMonthGroceryLists() {
		return new ResponseEntity<>(adminService.getCurrentMonthGroceryLists(), HttpStatus.OK);
	}

	@GetMapping("grocerylists/{year}/{month}")
	public ResponseEntity<?> getGroceryListsByYearAndMonth(@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(adminService.getGroceryListsByYearAndMonth(year, month), HttpStatus.OK);
	}

	@GetMapping("grocerylists")
	public ResponseEntity<?> getAllGroceryLists() {
		return new ResponseEntity<>(adminService.getAllGroceryLists(), HttpStatus.OK);
	}

	@GetMapping("monthlyexpenses/{year}/{month}")
	public ResponseEntity<?> getMonthlyExpenses(@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(adminService.calculateMonthlyExpenses(year, month), HttpStatus.OK);
	}

	@PostMapping("items")
	public ResponseEntity<?> addItem(@RequestBody Item item) {
		return new ResponseEntity<>(adminService.addItem(item), HttpStatus.CREATED);
	}

	@GetMapping("items")
	public ResponseEntity<?> getAllItems() {
		return new ResponseEntity<>(adminService.getAllItems(), HttpStatus.OK);
	}

	@GetMapping("currentmonth-items")
	public ResponseEntity<?> getCurrenMonthItems() {
		return new ResponseEntity<>(adminService.getCurrentMonthItems(), HttpStatus.OK);
	}

	@GetMapping("items/{year}/{month}")
	public ResponseEntity<?> getItemsByYearAndMonth(@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(adminService.getItemsByYearAndMonth(year, month), HttpStatus.OK);
	}

	@GetMapping("items/{category}")
	public ResponseEntity<?> getItemsByCategory(@PathVariable Category category) {
		return new ResponseEntity<>(adminService.getItemsByCategory(category), HttpStatus.OK);
	}

	@GetMapping("items/{category}/{year}/{month}")
	public ResponseEntity<?> getItemsByCategoryYearAndMonth(@PathVariable Category category, @PathVariable int year,
			@PathVariable int month) {
		return new ResponseEntity<>(adminService.getItemsByCategoryYearAndMonth(category, year, month), HttpStatus.OK);
	}

	@PutMapping("items")
	public ResponseEntity<?> updateItem(@RequestBody Item item) {
		return new ResponseEntity<>(adminService.updateItem(item), HttpStatus.OK);
	}

	@DeleteMapping("items/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(name = "id") int itemId) {
		adminService.deleteItem(itemId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("logout/{token}")
	public ResponseEntity<?> logout(@PathVariable String token) {
		tokensManager.deleteToken(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
