package com.contactmanager.smartcontactmanager.dao;

import com.contactmanager.smartcontactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
   @Query("from Contact as c where c.user.id =:userid")
    public List<Contact> findContactsByUser(@Param("userid") int userid);
}