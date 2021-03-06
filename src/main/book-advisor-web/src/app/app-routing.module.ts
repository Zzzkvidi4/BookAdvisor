import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {BookListComponent} from "./book-list/book-list.component";
import {BookReviewsComponent} from "./book-reviews/book-reviews.component";
import {LoginComponent} from "./login/login.component";
import {UserComponent} from "./user/user.component";

const routes: Routes = [
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  {path: 'index', component: BookListComponent},
  {path: 'reviews/:id', component: BookReviewsComponent},
  {path: 'accounts', component: LoginComponent},
  {path: 'users/:id', component: UserComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
