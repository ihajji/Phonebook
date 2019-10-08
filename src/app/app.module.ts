import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import {MatProgressSpinnerModule, MatFormFieldModule} from '@angular/material';


import { HomeComponent } from './components/home/home.component';
import { EditComponent } from './components/contact/edit/edit.component';
import { NewComponent } from './components/contact/new/new.component';
import { AppComponent } from './app.component';

import { ContactService } from './services/Contact.service';
import { ContactClient } from './clients/Contact.client';
import { ContactViewComponent } from './components/home/contact-view/contact-view.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormComponent } from './components/contact/form/form.component';
import { LimitStrLengthPipe } from './pipes/limit-str-length.pipe';
import { StrFilterPipe } from './pipes/str-filter.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RoutingResource} from './resources/Routing.resource';
import {ContactResource} from './resources/Contact.resource';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EditComponent,
    NewComponent,
    ContactViewComponent,
    FormComponent,
    LimitStrLengthPipe,
    StrFilterPipe
  ],
  imports: [
    BrowserModule, AppRoutingModule, HttpClientModule, ReactiveFormsModule,
    FormsModule, BrowserAnimationsModule, MatProgressSpinnerModule, MatFormFieldModule
  ],
  providers: [ContactService, ContactClient, RoutingResource, ContactResource],
  bootstrap: [AppComponent]
})
export class AppModule { }
