package com.smartshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartshopping.repo.AdministratorsRepository;
import com.smartshopping.repo.ConsumersRepository;
import com.smartshopping.repo.GroceryListsRepository;
import com.smartshopping.repo.ItemsRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Service
public abstract class UserService {
	@Autowired
	protected ConsumersRepository consumersRepository;
	@Autowired
	protected ItemsRepository itemsRepository;
	@Autowired
	protected GroceryListsRepository groceryListsRepository;
	@Autowired
	protected AdministratorsRepository administratorsRepository;

	public abstract boolean login(String name, String password);

	

}
