package com.nilesh.reecetech.addressbook.service;

import com.nilesh.reecetech.addressbook.dao.repository.AddressBookEntryRepository;
import com.nilesh.reecetech.addressbook.dao.repository.AddressBookRepository;
import com.nilesh.reecetech.addressbook.exception.ResourceNotFoundException;
import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AddressBookService {
    AddressBookRepository addressBookRepository;
    AddressBookEntryRepository addressBookEntryRepository;

    public AddressBook addAddressBook(AddressBook addressBook) {
        addressBook.getEntries().stream().forEach(entry -> entry.setAddressBook(addressBook));
       return  addressBookRepository.save(addressBook);
    }

    public AddressBook addAddressBookEntry(Long addressBookId, AddressBookEntry addressBookEntry) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new ResourceNotFoundException("AddressBook not found"));
        addressBook.getEntries().add(addressBookEntry);
        addressBookEntry.setAddressBook(addressBook);
        return addressBookRepository.save(addressBook);
    }

    public List<AddressBookEntry> getAllEntriesForAddressBook(Long addressBookId, int page, int pagesize) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(addressBookId);
        return addressBookEntryRepository.findAllByAddressBook(addressBook, PageRequest.of(page, pagesize));
    }

    public void deleteAddressBookEntry(Long addressBookId, Long entryId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new ResourceNotFoundException("AddressBook not found"));
        addressBook.getEntries().stream().filter(entry -> entry.getId().equals(entryId))
                .findFirst()
                .ifPresentOrElse(entry -> handleEntryDeletion(addressBook, entry),
                        () -> {throw new ResourceNotFoundException("Address entry not found exception");});
        addressBookRepository.saveAndFlush(addressBook);

    }

    public Set<AddressBookEntry> getUniqueAddressbookEntries() {
        Set<AddressBookEntry> uniqueEntries = new HashSet<>();
        List<AddressBookEntry> entries = addressBookEntryRepository.findUniqueEntries();
        entries.stream().forEach(ae -> uniqueEntries.add(ae));
        return uniqueEntries;
    }

    private void handleEntryDeletion(AddressBook addressBook, AddressBookEntry entry) {
        addressBook.getEntries().remove(entry);
        entry.setAddressBook(null);
    }
}
