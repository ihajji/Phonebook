package com.phonebook.service.contract;

import com.phonebook.model.bean.Contact;
import com.phonebook.model.exception.NotFoundException;

import java.util.List;

public interface ContactService {

    Contact getContact(Integer cId) throws NotFoundException;

    List<Contact> getContacts() throws NotFoundException;

    void deleteContact(Integer cId) throws NotFoundException;

    Contact createContact(Contact contact) throws NotFoundException;

    Contact updateContact(Contact contact) throws NotFoundException;
}
