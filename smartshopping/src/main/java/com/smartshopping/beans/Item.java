package com.smartshopping.beans;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String brand;
	@Enumerated(EnumType.STRING)
	private Category category;
	private double quantity;
	@Enumerated(EnumType.STRING)
	private Unit unit;
	private double price;
	private double cost;
	private Date date;

	public Item(String name, String brand, Category category, double quantity, Unit unit, double price) {
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.quantity = quantity;
		this.unit = unit;
		this.price = price;
		this.cost = price * quantity;

	}

}
