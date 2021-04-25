package com.nilesh.reecetech.addressbook.dao.repository;

import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressBookEntryRepository extends JpaRepository<AddressBookEntry, Long> {

    @Query("select distinct ae from AddressBookEntry ae")
    List<AddressBookEntry> findUniqueEntries();

}
