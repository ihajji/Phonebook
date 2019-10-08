import { Pipe, PipeTransform } from '@angular/core';
import {Contact} from '../models/Contact.model';

/**
 * Pipe that filter a list of contacts with a string.
 * This string must be a substring of the first name, the last name or the phone number of the contact.
 */
@Pipe({
  name: 'strFilter'
})
export class StrFilterPipe implements PipeTransform {

  transform(contacts: Contact[], str: string): Contact[] {
    if (str !== undefined && str.length > 0) {
      return contacts.filter( c => c.firstname.includes(str) ||
        c.lastname.includes(str) ||
        c.phoneNumber.includes(str));
    }
    return contacts;
  }

}
