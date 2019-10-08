import {Component, OnDestroy, OnInit} from '@angular/core';
import {ContactService} from '../../services/Contact.service';
import {Contact} from '../../models/Contact.model';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {RoutingResource} from '../../resources/Routing.resource';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  contacts: Contact[] = [];
  contactsSubscription: Subscription;
  searchContent: string;
  loading = false;
  errorMessage = '';
  printMessage = false;

  constructor(private contactService: ContactService,
              private router: Router,
              private routingResource: RoutingResource) { }

  ngOnInit() {
    this.initContactsSubscribtion();
  }

  getSearchValue() {
    return this.searchContent;
  }

  handleServerError(error) {
    this.loading = false;
    this.printMessage = true;
    this.errorMessage = error;
  }

  initContactsSubscribtion() {
    this.loading = true;
    this.contactsSubscription = this.contactService.contactSubject.subscribe(
      (contacts: Contact[]) => this.contacts = contacts.sort((a, b) => a.firstname.localeCompare(b.firstname))
    );
    this.contactService.getAll().then(
      () => { this.loading = false; this.printMessage = false; },
      (error) =>  { this.handleServerError(error); }
    );
  }

  onNewContact() {
    this.router.navigate([this.routingResource.HOME, this.routingResource.NEW_PHONEBOOK]);
  }

  ngOnDestroy(): void {
    this.contactsSubscription.unsubscribe();
  }
}
