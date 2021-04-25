package com.nilesh.reecetech.addressbook.controller;

import com.nilesh.reecetech.addressbook.exception.ResourceNotFoundException;
import com.nilesh.reecetech.addressbook.model.AddressBook;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;
import com.nilesh.reecetech.addressbook.service.AddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookControllerTest {

    @Mock
    private AddressBookService addressBookService;
    @Mock
    private AddressBook addressBook;
    @Mock
    private AddressBook resultAddressBook;
    @Mock
    private AddressBookEntry addressBookEntry;
    @InjectMocks
    private AddressBookController addressBookController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addAddressBook_success() {
        when(addressBookService.addAddressBook(addressBook)).thenReturn(resultAddressBook);
        var result  =  addressBookController.addAddressBook(addressBook);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void addAddressBook_error() {
        when(addressBookService.addAddressBook(addressBook)).thenThrow(new RuntimeException());
        var result  =  addressBookController.addAddressBook(addressBook);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void addAddressBookEntry_success() {
        when(addressBookService.addAddressBookEntry(1l, addressBookEntry)).thenReturn(addressBook);
        var result =
                addressBookController.addAddressBookEntry(1l, addressBookEntry);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void addAddressBookEntry_failureWhenApplicationError() {
        when(addressBookService.addAddressBookEntry(1l, addressBookEntry)).thenThrow(new RuntimeException());
        var result =
                addressBookController.addAddressBookEntry(1l, addressBookEntry);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void addAddressBookEntry_failureWhenClientError() {
        when(addressBookService.addAddressBookEntry(1l, addressBookEntry)).thenThrow(new ResourceNotFoundException("error msg"));
        var result =
                addressBookController.addAddressBookEntry(1l, addressBookEntry);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteAddressBookEntry_success() {
        //doNothing since controller is tested not service
        doNothing().when(addressBookService).deleteAddressBookEntry(1l, 2l);
        var result = addressBookController.deleteAddressBookEntry(1l, 2l);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void deleteAddressBookEntry_failure_clienterror() {
        doThrow(new ResourceNotFoundException("error msg")).when(addressBookService).deleteAddressBookEntry(1l, 2l);
        var result = addressBookController.deleteAddressBookEntry(1l, 2l);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteAddressBookEntry_failure_applicationError() {
        doThrow(new RuntimeException("error msg")).when(addressBookService).deleteAddressBookEntry(1l, 2l);
        var result = addressBookController.deleteAddressBookEntry(1l, 2l);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAddressbookById_success() {
        when(addressBookService.getAllEntriesForAddressBook(1l, 0, 5)).thenReturn(mock(ArrayList.class));
        var result = addressBookController.getAddressbookById(1l, 0, 5);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void getAddressbookById_failure() {
        when(addressBookService.getAllEntriesForAddressBook(1l, 0, 5)).thenThrow( new RuntimeException());
        var result = addressBookController.getAddressbookById(1l, 0, 5);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAddressBooks() {
    }

    @Test
    void getUniqueEntries() {
    }
}
