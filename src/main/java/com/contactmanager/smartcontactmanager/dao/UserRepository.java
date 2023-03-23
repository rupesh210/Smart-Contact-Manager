package com.contactmanager.smartcontactmanager.dao;

import com.contactmanager.smartcontactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select e from User e where e.email=:email")
    public User GetUserByUserName(@Param("email") String email);
}