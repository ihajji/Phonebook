package com.phonebook.rest.contact;

import com.phonebook.model.bean.Contact;
import com.phonebook.model.exception.NotFoundException;
import com.phonebook.service.contract.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST resource for the contacts.
 */

@CrossOrigin
@RestController
public class ContactResource {

    @Autowired
    @Qualifier("ContactService")
    private ContactService contactService;

    /**
     * Return the contact with  a given id
     *
     * @param id Contact identifier
     * @return A contact with the specific id
     */
    @GetMapping(value = "/contacts/{id}")
    public Contact get(@PathVariable int id) {
        try {
            return contactService.getContact(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Return all the contacts
     *
     * @return List of contacts
     */
    @GetMapping(value = "/contacts")
    public List<Contact> getAll() {
        try {
            return contactService.getContacts();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Delete a contact with a specific ID
     * @param id Identifier of the contact to delete
     */
    @DeleteMapping(value = "/contacts/{id}")
    public void delete(@PathVariable int id){
        try {
            contactService.deleteContact(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/contacts")
    public Contact update(@RequestBody Contact contact){
        try {
            return contactService.updateContact(contact);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/contacts")
    public Contact create(@RequestBody Contact contact) {
        try {
            return contactService.createContact(contact);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
