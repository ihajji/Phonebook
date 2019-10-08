import {Component, Input, OnInit} from '@angular/core';
import {Contact} from '../../../models/Contact.model';
import {Router} from '@angular/router';
import {RoutingResource} from '../../../resources/Routing.resource';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.component.html',
  styleUrls: ['./contact-view.component.css']
})
export class ContactViewComponent implements OnInit {
  @Input() contact: Contact;
  constructor(private router: Router,
              private routingResource: RoutingResource) { }

  ngOnInit() {
  }

  onEditContact(contact: Contact) {
    this.router.navigate([this.routingResource.HOME, this.routingResource.EDIT_PHONEBOOK, contact.contactId]);
  }
}
