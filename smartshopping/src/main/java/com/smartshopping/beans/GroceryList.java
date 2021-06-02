package com.smartshopping.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grocery_lists")
public class GroceryList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date date;
	@Enumerated(EnumType.STRING)
	private GroceryListStatus status;
	private String adminName;
	private String consumerName;
	private double totalCost;
	private String shopName;
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
	private List<Item> items = new ArrayList<>();

	public void addItem(Item i) {
		this.items.add(i);
		this.totalCost = this.items.stream().mapToDouble(Item::getCost).sum();
	}

}
