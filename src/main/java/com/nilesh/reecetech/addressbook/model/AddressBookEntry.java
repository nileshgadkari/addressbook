package com.nilesh.reecetech.addressbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "addressbook_entry"/*,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"firstName","lastName"})
}*/)
public class AddressBookEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="PHONE_NUMBERS")
    private List<String> phoneNumbers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_book_id")
    @JsonIgnore
    private AddressBook addressBook;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookEntry that = (AddressBookEntry) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumbers, that.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumbers);
    }
}
