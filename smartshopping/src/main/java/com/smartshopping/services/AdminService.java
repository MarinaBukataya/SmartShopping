package com.smartshopping.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.smartshopping.beans.Administrator;
import com.smartshopping.beans.Category;
import com.smartshopping.beans.Consumer;
import com.smartshopping.beans.GroceryList;
import com.smartshopping.beans.Item;
import com.smartshopping.exceptions.SignupException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Service
@Lazy
@Scope("prototype")
public class AdminService extends UserService {

	public int adminId;

	@Override
	public boolean login(String name, String password) {
		return administratorsRepository.ifAdministratorExists(name, password);
	}

	public int getAdminIdFromDB(String name, String password) {
		return this.administratorsRepository.getId(name, password);
	}

	public void signup(Administrator administrator) throws SignupException {
		if (administratorsRepository.existsByName(administrator.getName())) {
			throw new SignupException("This name already exists");
		} else
			administratorsRepository.save(administrator);
	}

	public List<Consumer> getAllConsumers() {
		return this.administratorsRepository.getOne(this.adminId).getConsumers();
	}

	public Consumer addConsumer(Consumer consumer) throws SignupException {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		Consumer newConsumer = new Consumer();
		if (consumersRepository.existsByName(consumer.getName())) {
			throw new SignupException("This name already exists");
		} else
		newConsumer = this.consumersRepository.save(consumer);
		administrator.getConsumers().add(consumer);
		this.administratorsRepository.saveAndFlush(administrator);
		return newConsumer;
	}

	public Consumer updateConsumer(Consumer consumer) {
		return this.consumersRepository.saveAndFlush(consumer);
	}

	public void deleteConsumer(int consumerId) {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		Consumer consumer = this.consumersRepository.getOne(consumerId);
		administrator.getConsumers().remove(consumer);
		this.administratorsRepository.saveAndFlush(administrator);
		System.out.println(this.consumersRepository.findAll());
	}

	public GroceryList getOneGroceryList(int groceryListId) {
		return this.groceryListsRepository.getOne(groceryListId);
	}

	public List<GroceryList> getAllGroceryLists() {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		List<GroceryList> allLists = this.groceryListsRepository.findAll();
		List<GroceryList> adminsLists = new ArrayList<>();
		for (GroceryList groceryList : allLists) {
			if (groceryList.getAdminName() != null) {
				if (groceryList.getAdminName().equals(administrator.getName())) {
					adminsLists.add(groceryList);
				}
			}
		}
		return adminsLists;
	}

	public List<GroceryList> getCurrentMonthGroceryLists() {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		List<GroceryList> allLists = this.groceryListsRepository.getAllOfCurrentMonth();
		List<GroceryList> result = new ArrayList<GroceryList>();
		for (GroceryList g : allLists) {
			if (g.getAdminName() != null) {
				if (g.getAdminName().equals(administrator.getName())) {
					result.add(g);
				}
			}
		}
		return result;
	}

	public List<GroceryList> getGroceryListsByYearAndMonth(int year, int month) {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		List<GroceryList> allLists = this.groceryListsRepository.findByYearAndMonth(year, month);
		List<GroceryList> result = new ArrayList<GroceryList>();
		for (GroceryList g : allLists) {
			if (g.getAdminName() != null) {
				if (g.getAdminName().equals(administrator.getName())) {
					result.add(g);
				}
			}
		}
		return result;
	}

	public double calculateMonthlyExpenses(int year, int month) {
		return getGroceryListsByYearAndMonth(year, month).stream().mapToDouble(GroceryList::getTotalCost).sum();
	}

	public GroceryList createGroceryList(GroceryList groceryList) {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		groceryList.setAdminName(administrator.getName());
		GroceryList gList = this.groceryListsRepository.save(groceryList);
		administrator.getGroceries().add(groceryList);
		this.administratorsRepository.saveAndFlush(administrator);
		return gList;
	}

	public GroceryList updateGroceryList(GroceryList groceryList) {
		return this.groceryListsRepository.saveAndFlush(groceryList);
	}

	public void deleteGroceryList(int groceryListId) {
		Administrator administrator = this.administratorsRepository.getOne(this.adminId);
		GroceryList gl = this.groceryListsRepository.getOne(groceryListId);
		administrator.getGroceries().remove(gl);
		this.administratorsRepository.saveAndFlush(administrator);
	}

	public Item addItem(Item item) {
		return this.itemsRepository.save(item);
	}

	public Item updateItem(Item item) {
		return this.itemsRepository.saveAndFlush(item);
	}

	public void deleteItem(int id) {
		Item item = this.itemsRepository.getOne(id);
		for (GroceryList groceryList : this.getAllGroceryLists()) {
			for (Item i : groceryList.getItems()) {
				if (i.getId() == id) {
					groceryList.getItems().remove(item);
					return;
				}
			}
		}
	}

	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<>();
		for (GroceryList groceryList : this.getAllGroceryLists()) {
			items.addAll(groceryList.getItems());
		}
		return items;
	}

	public List<Item> getCurrentMonthItems() {
		List<Item> items = new ArrayList<>();
		for (GroceryList groceryList : this.getCurrentMonthGroceryLists()) {
			items.addAll(groceryList.getItems());
		}
		return items;
	}

	public List<Item> getItemsByYearAndMonth(int year, int month) {
		List<Item> items = new ArrayList<>();
		for (GroceryList groceryList : this.getGroceryListsByYearAndMonth(year, month)) {
			items.addAll(groceryList.getItems());
		}
		return items;
	}

	public List<Item> getItemsByCategoryYearAndMonth(Category category, int year, int month) {
		return this.getItemsByYearAndMonth(year, month).stream().filter(x -> x.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	public List<Item> getItemsByCategory(Category category) {
		return getCurrentMonthItems().stream().filter(x -> x.getCategory().equals(category))
				.collect(Collectors.toList());
	}

}
