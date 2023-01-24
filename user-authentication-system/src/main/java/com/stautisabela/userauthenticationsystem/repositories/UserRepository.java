package com.stautisabela.userauthenticationsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stautisabela.userauthenticationsystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	// example on how to create personalised queries
	@Query("SELECT user FROM User WHERE user.userName =: userName")
	User findByUsername(@Param("userName") String userName);
}
