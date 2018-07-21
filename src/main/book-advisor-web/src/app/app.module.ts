import { BrowserModule } from '@angular/platform-browser';
import {InjectionToken, NgModule} from '@angular/core';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { BookListComponent } from './book-list/book-list.component';
import { BookReviewsComponent } from './book-reviews/book-reviews.component';
import {
  MatButtonModule, MatCardModule, MatCheckboxModule, MatIconModule, MatInputModule, MatPaginatorModule,
  MatProgressSpinnerModule,
  MatTableModule,
  MatDialogModule, MatDialog, MatTabsModule, MatFormFieldModule
} from "@angular/material";
import {BookSearchService} from "./service/book-searcher/book-search.service";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ReviewRetrieverService} from "./service/review-retriever/review-retriever.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CdkTableModule} from "@angular/cdk/table";
import { LoginComponent } from './login/login.component';
import {LoginService} from "./service/loginner/login.service";
import { UserComponent } from './user/user.component';
import {UserService} from "./service/user-service/user.service";
import { ReviewListComponent } from './review-list/review-list.component';


@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookReviewsComponent,
    LoginComponent,
    UserComponent,
    ReviewListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
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
    MatDialogModule,
    MatTabsModule,
    MatFormFieldModule,
  ],
  providers: [
    HttpClient,
    BookSearchService,
    ReviewRetrieverService,
    MatDialog,
    LoginService,
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
