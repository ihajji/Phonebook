import {Injectable} from '@angular/core';
import {Contact} from '../models/Contact.model';
import {Subject, throwError} from 'rxjs';
import {ContactClient} from '../clients/Contact.client';

@Injectable()
export class ContactService {
  private contacts: Contact[] = [];
  contactSubject = new Subject<Contact[]>();

  constructor(private contactClient: ContactClient) {}

  /**
   * Notify all observer that there are a change in the contact's list
   */
  emitContacts(): void {
    this.contactSubject.next(this.contacts.slice());
  }

  /**
   * Get all contacts from the database
   */
  async getAll(): Promise<void | Contact[]> {
    return await this.contactClient.readAll().then(
      newContacts => {
        this.contacts = newContacts;
        this.emitContacts();
      }, (error) => {
        return throwError( error.toString()).toPromise();
      });
  }

  get(id: number) {
    return this.contacts.find((c) => c.contactId === id);
  }

  /**
   * Add contact on database and contact's list
   * @param contact A new contact
   */
  async add(contact: Contact): Promise<void | Contact> {
    return await this.contactClient.create(contact).then(
      newContact => {
        this.contacts.push(newContact);
        this.emitContacts();
      },
      (err) => {
        return throwError( err.toString()).toPromise();
      }
    );
  }

  /**
   * Update contact on database and contact's list
   * @param contact Updated contact
   */
  async update(contact: Contact): Promise<void | Contact> {
    return await this.contactClient.update(contact).then(
      newContact => {
        this.deleteFromContactList(contact.contactId);
        this.contacts.push(newContact);
        this.emitContacts();
      },
      (err) => {
        return throwError( err.toString()).toPromise();
      }
    );
  }

  /**
   * Delete contact from database and contact's list
   * @param contactId Identifier of the contact to delete
   */
  delete(contactId: number): void {
    this.contactClient.delete(contactId).subscribe(
      () => {
        this.deleteFromContactList(contactId);
      }
    );
  }

  /**
   * Find and delete contact on contact's list
   * @param contactId Identifier of the contact to delete
   */
  deleteFromContactList(contactId: number) {
    const contactIdx = this.contacts.findIndex((c) => c.contactId === contactId);
    this.contacts.splice(contactIdx, 1);
  }
}
