import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Contact} from '../../../models/Contact.model';
import {ContactService} from '../../../services/Contact.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RoutingResource} from '../../../resources/Routing.resource';
import {ContactResource} from '../../../resources/Contact.resource';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {
  formGroup: FormGroup;
  @Input() create;
  contact: Contact = undefined;
  loading = false;

  // Handle errors
  printErrorMsg = false;
  errorMsgs: string[] = [];

  constructor(private formBuilder: FormBuilder,
              private contactService: ContactService,
              private router: Router,
              private route: ActivatedRoute,
              private routingResource: RoutingResource,
              public contactResource: ContactResource) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
      phonenumber: ['', [Validators.required, Validators.pattern('^[+][0-9]+ [0-9]+ [0-9]{6,}')]]
    });

    // For update form, get the id from the url
    if (!this.create) {
      const id = this.route.snapshot.params.id;
      this.contact = this.contactService.get(+id);
      if (this.contact === undefined) {
        this.router.navigate( [this.routingResource.HOME]);
      } else {
        // Update html form fields value
        this.formGroup.get(this.contactResource.FIRSTNAME).setValue(this.contact.firstname);
        this.formGroup.get(this.contactResource.LASTNAME).setValue(this.contact.lastname);
        this.formGroup.get(this.contactResource.PHONENUMBER).setValue(this.contact.phoneNumber);
      }
    }
  }

  /**
   * Check the validity of the forms values:
   *  - Check if a phone number is given
   *  - Check if the phone number respect a specific format
   *  - Check if a first name or last name is given
   *
   * @param firstname The given first name
   * @param lastname The given last name
   * @param phonenumber The given phone number
   */
  checkFormValidity(firstname: string, lastname: string, phonenumber: string): boolean {
    let isValid = true;

    if (this.formGroup.invalid) {
      isValid = false;
      if (phonenumber === '') {
        this.errorMsgs.push('Please enter a phone number.');
      } else {
        this.errorMsgs.push('The phone number need to satisfy the format +X X Y ' +
          'where X is a set of numbers and Y a set of at least 6 numbers');
      }
    }

    if (firstname === '' && lastname === '') {
      isValid = false;
      this.errorMsgs.push('Please enter a first name or a last name.');
    }

    return isValid;
  }

  /**
   * Return to home page
   */
  onReturn() {
    this.router.navigate([this.routingResource.HOME]);
  }

  /**
   * Handle server error
   * @param error The message to print to the user
   */
  handleServerError(error) {
    this.loading = false;
    this.printErrorMsg = true;
    this.errorMsgs.push(error.toString());
  }

  onSubmit() {
    this.errorMsgs = [];
    const firstname = this.formGroup.get(this.contactResource.FIRSTNAME).value.toString().trim();
    const lastname = this.formGroup.get(this.contactResource.LASTNAME).value.toString().trim();
    const phonenumber = this.formGroup.get(this.contactResource.PHONENUMBER).value.toString().trim();

    const valid = this.checkFormValidity(firstname, lastname, phonenumber);

    if (!valid) {
      this.printErrorMsg = true;
    } else {
      this.printErrorMsg = false;
      this.loading = true;

      const contact = new Contact(firstname, lastname, phonenumber);
      if (this.create) {
        this.contactService.add(contact).then(
          () => { this.router.navigate( [this.routingResource.HOME]); },
          (error) =>  { this.handleServerError(error); }
        );
      } else {
        contact.contactId = this.contact.contactId;
        this.contactService.update(contact).then(
          () => { this.router.navigate( [this.routingResource.HOME]); },
          (error) =>  { this.handleServerError(error); }
        );
      }
    }
  }
}
