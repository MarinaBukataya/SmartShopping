package com.smartshopping.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.smartshopping.beans.Consumer;
import com.smartshopping.beans.GroceryList;
import com.smartshopping.beans.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Service
@Lazy
@Scope("prototype")
public class ConsumerService extends UserService {

	private int consumerId;

	@Override
	public boolean login(String name, String password) {
		return this.consumersRepository.ifConsumerExists(name, password);
	}

	public int getConsumerIdFromDB(String name, String password) {
		return this.consumersRepository.getId(name, password);
	}

	public List<GroceryList> getCurrentMonthGroceryLists() {
		Consumer consumer = this.consumersRepository.getOne(this.consumerId);
		String consumerName = consumer.getName();
		return this.groceryListsRepository.getAllOfCurrentMonth().stream()
				.filter(g -> g.getConsumerName().equals(consumerName)).collect(Collectors.toList());
	}

	public GroceryList getOneGroceryList(int groceryListId) {
		return this.groceryListsRepository.getOne(groceryListId);
	}

	public GroceryList updateGroceryList(GroceryList groceryList) {
		return this.groceryListsRepository.saveAndFlush(groceryList);
	}

	public Item addItem(Item item) {
		return this.itemsRepository.save(item);
	}

	public Item updateItem(Item item) {
		return this.itemsRepository.saveAndFlush(item);
	}

	public void deleteItem(int id) {
		this.itemsRepository.deleteById(id);
	}

}
