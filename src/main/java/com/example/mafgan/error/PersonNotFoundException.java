package com.example.mafgan.error;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String person_not_available) {
        super(person_not_available);
    }
}
