package com.nilesh.reecetech.addressbook.dao.repository;

import com.nilesh.reecetech.addressbook.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
}
