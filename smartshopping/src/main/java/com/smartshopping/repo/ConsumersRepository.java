package com.smartshopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartshopping.beans.Consumer;

public interface ConsumersRepository extends JpaRepository<Consumer, Integer> {
	
	boolean existsByName(String username);

	boolean existsByPassword(String password);

	@Query("SELECT CASE WHEN count(c)> 0 THEN true ELSE false END FROM Consumer c WHERE LOWER(c.name) like LOWER(:name) AND LOWER(c.password) like LOWER(:password)")
	boolean ifConsumerExists(@Param("name") String name, @Param("password") String password);

	@Query("SELECT id AS id FROM Consumer c WHERE c.name = :name AND c.password = :password")
	int getId(@Param(value = "name") String name, @Param(value = "password") String password);

}
