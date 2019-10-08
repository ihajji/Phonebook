import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {EditComponent} from './components/contact/edit/edit.component';
import {NewComponent} from './components/contact/new/new.component';

const routes: Routes = [
  {path: 'phonebook', children: [
      {path: '', component: HomeComponent},
      {path: 'edit/:id', component: EditComponent},
      {path: 'new', component: NewComponent},
    ]},
  {path: '**', redirectTo: 'phonebook'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
