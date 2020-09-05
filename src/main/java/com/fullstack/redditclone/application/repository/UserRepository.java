package com.fullstack.redditclone.application.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.redditclone.application.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	
	@Query(value = "SELECT * FROM users u WHERE u.first_name = ?1 AND u.last_name = ?2",nativeQuery = true)
	UserEntity findUserByFirstNameAndLastName(String firstName, String lastName);

	UserEntity findUserByUserId(String userId);
	
	
	@Query(value = "SELECT user_id FROM users u WHERE u.first_name = ?1 AND u.last_name = ?2",nativeQuery = true)
	String findUserIdByFirstNameAndLastName(String firstName, String lastName);

}
