package com.nilesh.reecetech.addressbook.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResourceNotFoundException extends RuntimeException{
    private String message;
}
