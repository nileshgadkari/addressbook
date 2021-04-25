package com.nilesh.reecetech.addressbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilesh.reecetech.addressbook.model.AddressBookEntry;

import java.io.StringWriter;
import java.util.List;

public class Temp {
    public static void main(String[] args) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String output;
        AddressBookEntry addressBookEntry = new AddressBookEntry();
        addressBookEntry.setId(22l);
        addressBookEntry.setFirstName("First name");
        addressBookEntry.setLastName("Last name");
        addressBookEntry.setPhoneNumbers(List.of("1122","444"));
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, addressBookEntry);
        System.out.println(writer.toString());
    }
}
