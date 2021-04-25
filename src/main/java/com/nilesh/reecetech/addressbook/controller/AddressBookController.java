package com.nilesh.reecetech.addressbook.controller;

import com.nilesh.reecetech.addressbook.dao.repository.AddressBookRepository;
import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import com.nilesh.reecetech.addressbook.service.AddressBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/v1/addressbooks")
@AllArgsConstructor
public class AddressBookController {
    private AddressBookRepository addressBookRepository;
    private AddressBookService addressBookService;

    @PostMapping
    public ResponseEntity<AddressBook> addAddressBook(@RequestBody AddressBook addressBook){
        AddressBook savedAddressBook = addressBookService.addressBook(addressBook);
        return ResponseEntity.ok().body(savedAddressBook);
    }

    @PostMapping("/{id}/entries")
    public ResponseEntity<AddressBook> addAddressBookEntry(@PathVariable("id") Long addressBookId, @RequestBody AddressBookEntry addressBookEntry){
       AddressBook addressBook = addressBookService.addAddressBookEntry(addressBookId, addressBookEntry);
        return ResponseEntity.ok().body(addressBook);
    }

    @DeleteMapping("/{addressBookId}/entries/{entryId}")
    public ResponseEntity<HttpStatus> deleteAddressBookEntry(@PathVariable("addressBookId") Long addressBookId, @PathVariable("entryId") Long entryId) {
        addressBookService.deleteAddressBookEntry(addressBookId, entryId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getAddressbookById(@PathVariable("id") Long id) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Addressbook not found for id "+id));
        return ResponseEntity.ok().body(addressBook);
    }

    @GetMapping
    public List<AddressBook> getAddressBooks() {
        return addressBookService.getAllAddressBooks();
    }

    @GetMapping("/unique")
    public Set<AddressBookEntry> getUniqueEntries() {
        return addressBookService.getUniqueAddressbookEntries();
    }

}
