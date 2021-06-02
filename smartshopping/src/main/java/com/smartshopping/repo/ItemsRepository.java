package com.smartshopping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartshopping.beans.Item;

public interface ItemsRepository extends JpaRepository<Item, Integer> {
	
	@Query("select i from Item i where year(i.date) = year(current_date) and month(i.date) = month(current_date)")
	List<Item> getAllOfCurrentMonth();
	
	@Query("select i from Item i where year(i.date) = ?1 and month(i.date) = ?2")
	List<Item> findByYearAndMonth(@Param("year") int year, @Param ("month") int month);

}
