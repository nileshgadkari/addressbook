package com.nilesh.reecetech.addressbook.dao.repository;

import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressBookEntryRepository extends PagingAndSortingRepository<AddressBookEntry, Long> {

    @Query("select distinct ae from AddressBookEntry ae")
    List<AddressBookEntry> findUniqueEntries();

    List<AddressBookEntry> findAllByAddressBook(AddressBook addressBook, Pageable pageable);

}
