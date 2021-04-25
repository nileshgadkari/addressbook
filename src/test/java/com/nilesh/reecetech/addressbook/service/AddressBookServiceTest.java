package com.nilesh.reecetech.addressbook.service;

import com.nilesh.reecetech.addressbook.dao.repository.AddressBookEntryRepository;
import com.nilesh.reecetech.addressbook.dao.repository.AddressBookRepository;
import com.nilesh.reecetech.addressbook.exception.ResourceNotFoundException;
import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceTest {

    @Mock
    private AddressBookRepository addressBookRepository;
    @Mock
    private AddressBookEntryRepository addressBookEntryRepository;
    @Mock
    private AddressBook addressBook;
    @Mock
    private AddressBook resultAddressBook;
    @InjectMocks
    private AddressBookService addressBookService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void addAddressBook() {
        when(addressBookRepository.save(addressBook)).thenReturn(resultAddressBook);
        var result = addressBookService.addAddressBook(addressBook);
        assertEquals(resultAddressBook, result);
    }

    @Test
    void addAddressBookEntry_success() {
        AddressBook addressBook = new AddressBook();
        AddressBookEntry newAddressBookEntry = new AddressBookEntry();
        newAddressBookEntry.setId(10l);

        when(addressBookRepository.findById(1l)).thenReturn(Optional.of(addressBook));
        when(addressBookRepository.save(addressBook)).thenReturn(addressBook);

        AddressBook updatedAddressBook = addressBookService.addAddressBookEntry(1l, newAddressBookEntry);
        assertEquals(10, updatedAddressBook.getEntries().get(0).getId());
    }

    @Test
    void addAddressBookEntry_failureWhenAddressNotFound() {
        AddressBookEntry newAddressBookEntry = new AddressBookEntry();
        newAddressBookEntry.setId(10l);

        when(addressBookRepository.findById(1l)).thenThrow(new ResourceNotFoundException("error msg"));

        assertThrows(ResourceNotFoundException.class, () -> addressBookService.addAddressBookEntry(1l, newAddressBookEntry));
    }

    @Test
    void addAddressBookEntry_failureWhenDBIssue() {
        AddressBookEntry newAddressBookEntry = new AddressBookEntry();
        newAddressBookEntry.setId(10l);

        when(addressBookRepository.findById(1l)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> addressBookService.addAddressBookEntry(1l, newAddressBookEntry));
    }

    @Test
    void deleteAddressBookEntry() {
        AddressBook addressBook = new AddressBook();
        AddressBookEntry addressBookEntry = new AddressBookEntry();
        addressBookEntry.setId(10l);
        addressBook.getEntries().add(addressBookEntry);

        when(addressBookRepository.findById(1l)).thenReturn(Optional.of(addressBook));

        addressBookService.deleteAddressBookEntry(1l, 10l);
        assertTrue(addressBook.getEntries().isEmpty());
    }

    @Test
    void deleteAddressBookEntry_failureWhenAddressBookEntryNotFound() {

        AddressBook addressBook = new AddressBook();

        AddressBookEntry addressBookEntry1 = new AddressBookEntry();
        addressBookEntry1.setId(10l);
        addressBook.getEntries().add(addressBookEntry1);

        AddressBookEntry addressBookEntry2 = new AddressBookEntry();
        addressBookEntry2.setId(11l);
        addressBook.getEntries().add(addressBookEntry2);

        when(addressBookRepository.findById(1l)).thenReturn(Optional.of(addressBook));
        assertThrows(ResourceNotFoundException.class, () -> addressBookService.deleteAddressBookEntry(1l, 12l));
    }

    @Test
    void deleteAddressBookEntry_failureWhenAddressBookNotFound() {
        when(addressBookRepository.findById(1l)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> addressBookService.deleteAddressBookEntry(1l, 10l));
    }

    @Test
    void getAllEntriesForAddressBook() {
        AddressBook addressBook = new AddressBook();

        AddressBookEntry addressBookEntry1 = new AddressBookEntry();
        addressBookEntry1.setId(10l);
        addressBook.getEntries().add(addressBookEntry1);

        AddressBookEntry addressBookEntry2 = new AddressBookEntry();
        addressBookEntry2.setId(11l);
        addressBook.getEntries().add(addressBookEntry2);

        AddressBookEntry addressBookEntry3 = new AddressBookEntry();
        addressBookEntry3.setId(12l);
        addressBook.getEntries().add(addressBookEntry3);

        when(addressBookEntryRepository.findAllByAddressBook(any(AddressBook.class), any(PageRequest.class)))
                .thenReturn(List.of(addressBookEntry1, addressBookEntry2));
        List<AddressBookEntry> entries = addressBookService.getAllEntriesForAddressBook(1l, 0, 2);
        assertEquals(2, entries.size());
        assertEquals(10l, entries.get(0).getId());
        assertEquals(11l, entries.get(1).getId());
    }

    @Test
    void getAllAddressBooks() {
    }

    @Test
    void getUniqueAddressbookEntries() {
    }
}
