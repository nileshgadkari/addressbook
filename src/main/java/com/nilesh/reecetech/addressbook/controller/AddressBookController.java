package com.nilesh.reecetech.addressbook.controller;

import com.nilesh.reecetech.addressbook.exception.ResourceNotFoundException;
import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import com.nilesh.reecetech.addressbook.service.AddressBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/addressbooks")
@AllArgsConstructor
@Slf4j
public class AddressBookController {
    private AddressBookService addressBookService;

    @PostMapping
    public ResponseEntity<AddressBook> addAddressBook(@RequestBody AddressBook addressBook){
        try {
            var newAddressBook = addressBookService.addAddressBook(addressBook);
            var response = new ResponseEntity<>(newAddressBook, HttpStatus.CREATED);
            log.info("Address book created successfully. id={}", addressBook.getId());
            return response;
        }catch (Exception ex) {
            log.error("Error creating address book. message={}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/entries")
    public ResponseEntity<AddressBook> addAddressBookEntry(@PathVariable("id") Long addressBookId, @RequestBody AddressBookEntry addressBookEntry){
        try {
            var updatedAddressBook = addressBookService.addAddressBookEntry(addressBookId, addressBookEntry);
            log.info("Address book updated successfully. id={}", updatedAddressBook.getId());
            return ResponseEntity.accepted().body(updatedAddressBook);
        }catch (ResourceNotFoundException ex) {
            log.error("Error adding address book entry for address book id={}", addressBookId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception ex) {
            log.error("Error adding entry to address book id={}", addressBookId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{addressBookId}/entries/{entryId}")
    public ResponseEntity<HttpStatus> deleteAddressBookEntry(@PathVariable("addressBookId") Long addressBookId, @PathVariable("entryId") Long entryId) {
        try {
            addressBookService.deleteAddressBookEntry(addressBookId, entryId);
            log.info("Address book entry id={} deleted successfully from Address book id={}", entryId, addressBookId);
            return ResponseEntity.accepted().build();
        }catch (ResourceNotFoundException ex) {
            log.error("Error deleting address book entry id={} for address book id={}", entryId, addressBookId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception ex) {
            log.error("Error deleting address book entry id={} for address book id={}", entryId, addressBookId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AddressBookEntry>> getAddressbookById(@PathVariable("id") Long addressbookId, @RequestParam int page, @RequestParam int pagesize) {
        try {
            var addressbookEntries = addressBookService.getAllEntriesForAddressBook(addressbookId, page, pagesize);
            log.debug("Address book entries retrieved successfully for address book id={}", addressbookId);
            return ResponseEntity.accepted().body(addressbookEntries);
        }catch (Exception ex) {
            log.error("Error fetching address book entries for address book id={}", addressbookId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/unique")
    public ResponseEntity<Set<AddressBookEntry>> getUniqueEntries() {
        try{
            var uniqueAddressbookEntries = addressBookService.getUniqueAddressbookEntries();
            return ResponseEntity.accepted().body(uniqueAddressbookEntries);
        }catch(Exception ex) {
            log.error("Error fetching unique address book entries");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
