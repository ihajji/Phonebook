package com.phonebook.service.impl;

import com.phonebook.model.bean.Contact;
import com.phonebook.model.exception.NotFoundException;
import com.phonebook.service.contract.ContactService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the service of the contact class, test order:
 *
 * 1.Create success
 *
 * 2.Get success
 * 3.Get failed
 *
 * 4.Update success
 * 5.Update failed
 *
 * 6.Delete failed
 * 7.GetAll and delete
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.phonebook.*")
class ContactServiceImplTest {

    private static final int BAD_ID = -1;
    private Contact createContact = null;

    @Autowired
    @Qualifier("ContactService")
    private ContactService contactService;

    @Test
    @Order(1)
    void createContactSuccess() {
        System.out.println(contactService);
        try {
            Contact contact = new Contact();
            contact.setFirstname("TEST_FIRSTNAME");
            contact.setLastname("TEST_LASTNAME");
            contact.setPhoneNumber("TEST_PHONENUMBER");
            createContact = contactService.createContact(contact);
            assertTrue(createContact.getContactId() > 0);
            contact.setContactId(createContact.getContactId());
            assertEquals(contact, createContact);
        } catch (NotFoundException e) {
            fail("Fail to create contact");
        }
    }

    @Test
    @Order(2)
    void getContactCorrectId() {
        try {
            Contact contact = contactService.getContact(createContact.getContactId());
            assertEquals(createContact, contact);
        } catch (NotFoundException e) {
            fail("Fail to retrieve contact");
        }
    }

    @Test
    @Order(3)
    void getContactBadId() {
        assertThrows(NotFoundException.class, () -> contactService.getContact(BAD_ID));
    }

    @Test
    @Order(4)
    void updateContactSuccess() {
        try {
            Contact contact = new Contact();
            contact.setContactId(createContact.getContactId());
            contact.setFirstname("TEST_FIRSTNAME_UPDATE");
            contact.setLastname("TEST_LASTNAME_UPDATE");
            contact.setPhoneNumber("TEST_PHONENUMBER_UPDATE");

            createContact = contactService.updateContact(contact);
            assertEquals(createContact, contact);
        } catch (NotFoundException e) {
            fail("Fail to update contact");
        }
    }

    @Test
    @Order(5)
    void updateContactBadId() {
            Contact contact = new Contact();
            contact.setContactId(BAD_ID);
            contact.setFirstname("TEST_FIRSTNAME_UPDATE");
            contact.setLastname("TEST_LASTNAME_UPDATE");
            contact.setPhoneNumber("TEST_PHONENUMBER_UPDATE");

            assertThrows(NotFoundException.class, () -> contactService.updateContact(contact));
    }

    private void deleteContactSuccess() {
        try {
            contactService.deleteContact(createContact.getContactId());
        } catch (NotFoundException e) {
            fail("Fail to delete contact");
        }
    }

    @Test
    @Order(7)
    void deleteContactBadId() {
        assertThrows(NotFoundException.class, () -> contactService.deleteContact(BAD_ID));
    }

    @Test
    @Order(6)
    void getContacts() {
        try {
            int listSize = contactService.getContacts().size();
            deleteContactSuccess();
            assertEquals(listSize - 1, contactService.getContacts().size());
        } catch (NotFoundException e) {
            fail("Fail to retrieve contacts");
        }
    }
}