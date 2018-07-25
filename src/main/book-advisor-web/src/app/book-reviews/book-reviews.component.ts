import {AfterViewInit, Component, OnInit} from '@angular/core';
import {BookSearchService} from "../service/book-searcher/book-search.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Book} from "../model/Book";
import {ReviewRetrieverService} from "../service/review-retriever/review-retriever.service";
import {Review} from "../model/Review";
import {MatTableDataSource} from "@angular/material";
import {LoginService} from "../service/loginner/login.service";
import {UserService} from "../service/user-service/user.service";

@Component({
  selector: 'app-book-reviews',
  templateUrl: './book-reviews.component.html',
  styleUrls: ['./book-reviews.component.css']
})
export class BookReviewsComponent implements OnInit, AfterViewInit {
  ngAfterViewInit(): void {

  }

  book: Book;
  reviews: Review[] = null;
  isAuthenticated: boolean;
  isInFavourite: boolean = false;
  isQuering: boolean = true;
  isReady: boolean = false;
  isSuccess: boolean = false;
  isError: boolean = false;

  constructor(
    private bookSearchService: BookSearchService,
    private location: ActivatedRoute,
    private reviewRetrieverService: ReviewRetrieverService,
    private router: Router,
    private userService: UserService,
  ) { }

  ngOnInit() {
    let id = +this.location.snapshot.paramMap.get("id");
    this.book = this.bookSearchService.getBook(id);
    if (!this.book){
      this.router.navigate(["/index"]);
    }
    console.log(id);
    console.log(this.book);
    this.isAuthenticated = LoginService.isAuthorized;
    this.userService.isInFavourite(id, this.book).subscribe(
      resp => {
        console.log(resp);
        this.isInFavourite = resp.body.data;
      },
      error => {
        console.log(error);
      }
    );
    this.isQuering = true;
    this.isReady = false;
    this.reviewRetrieverService.retrieveReviews(this.book).subscribe(
      resp => {
        console.log(resp);
        this.reviews = resp.body;
        this.isQuering = false;
        this.isReady = true;
        this.isSuccess = true;
        this.isError = false;
      },
      error => {
        console.log(error);
        this.isQuering = false;
        this.isError = true;
        this.isReady = true;
        this.isSuccess = false;      }
    )
  }

  addToFavourite() {
    if (!this.isAuthenticated) {
      this.router.navigate(["/accounts"]);
    } else {
      console.log(BookSearchService.selector);
      this.book.selector = BookSearchService.selector;
      this.userService.addToFavourite(LoginService.userId, this.book).subscribe(
        resp => {
          this.isInFavourite = resp.body.data;
        },
        error => {
          console.log(error);
        }
      );
    }
  }
}
