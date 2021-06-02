package com.smartshopping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartshopping.beans.GroceryList;

public interface GroceryListsRepository extends JpaRepository<GroceryList, Integer> {

	@Query("select g from GroceryList g where year(g.date) = year(current_date) and month(g.date) = month(current_date)")
	List<GroceryList> getAllOfCurrentMonth();

	@Query("select g from GroceryList g where year(g.date) = ?1 and month(g.date) = ?2")
	List<GroceryList> findByYearAndMonth(@Param("year") int year, @Param ("month") int month);

}
