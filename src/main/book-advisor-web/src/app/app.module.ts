import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { BookListComponent } from './book-list/book-list.component';
import { BookReviewsComponent } from './book-reviews/book-reviews.component';


@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookReviewsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
