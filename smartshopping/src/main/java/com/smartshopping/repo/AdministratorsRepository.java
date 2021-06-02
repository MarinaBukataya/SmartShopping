package com.smartshopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartshopping.beans.Administrator;

public interface AdministratorsRepository extends JpaRepository<Administrator, Integer> {

	boolean existsByName(String username);

	boolean existsByPassword(String password);

	@Query("SELECT CASE WHEN count(a)> 0 THEN true ELSE false END FROM Administrator a WHERE LOWER(a.name) like LOWER(:name) AND LOWER(a.password) like LOWER(:password)")
	boolean ifAdministratorExists(@Param("name") String name, @Param("password") String password);

	@Query("SELECT id AS id FROM Administrator a WHERE a.name = :name AND a.password = :password")
	int getId(@Param(value = "name") String name, @Param(value = "password") String password);

}
