package com.phonebook.service.impl;

import com.phonebook.dao.contract.Dao;
import com.phonebook.model.bean.Contact;
import com.phonebook.model.exception.NotFoundException;
import com.phonebook.service.contract.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact service, use contact dao to get information from database,
 * verify the validity of these information and apply logic on theme
 */
@Component("ContactService")
public class ContactServiceImpl implements ContactService {
    private static final String NOT_FOUND_ERROR = "Contact with id %s not found.";

    @Autowired
    @Qualifier("ContactDao")
    private Dao<Contact> contactDao;

    @Override
    public Contact getContact(Integer cId) throws NotFoundException {
        Contact contact;

        if (cId < 1) {
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, cId));
        }

        contact = contactDao.get(cId);

        if (contact == null) {
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, cId));
        }

        return contact;
    }

    @Override
    public List<Contact> getContacts() throws NotFoundException{
        return new ArrayList<>(contactDao.getAll());
    }

    @Override
    public void deleteContact(Integer cId) throws NotFoundException {
        if (cId == null || cId < 1) {
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, cId));
        }

        int returnCode = contactDao.delete(cId);

        if(returnCode == 0){
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, cId));
        }
    }

    @Override
    public Contact createContact(Contact contact) throws NotFoundException{
        int id = contactDao.save(contact);
        contact.setContactId(id);
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact) throws NotFoundException{
        if (contact != null && contact.getContactId() != null &&  contact.getContactId() < 1) {
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, contact.getContactId()));
        }

        int returnCode = contactDao.update(contact);

        if(returnCode == 0 && contact != null){
            throw new NotFoundException(String.format(NOT_FOUND_ERROR, contact.getContactId()));
        }

        return contact;
    }
}
