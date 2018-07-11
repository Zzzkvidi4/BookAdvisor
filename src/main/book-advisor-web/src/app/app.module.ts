import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { BookListComponent } from './book-list/book-list.component';
import { BookReviewsComponent } from './book-reviews/book-reviews.component';
import {
  MatButtonModule, MatCardModule, MatCheckboxModule, MatIconModule, MatInputModule, MatPaginatorModule,
  MatProgressSpinnerModule,
  MatTableModule
} from "@angular/material";
import {BookSearchService} from "./service/book-searcher/book-search.service";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ReviewRetrieverService} from "./service/review-retriever/review-retriever.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {CdkTableModule} from "@angular/cdk/table";


@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookReviewsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatCardModule,
    CdkTableModule,
    MatProgressSpinnerModule,
    MatCheckboxModule,
    MatButtonModule,
  ],
  providers: [
    HttpClient,
    BookSearchService,
    ReviewRetrieverService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
