import {AfterViewInit, Component, OnInit} from '@angular/core';
import {BookSearchService} from "../service/book-searcher/book-search.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Book} from "../model/Book";
import {ReviewRetrieverService} from "../service/review-retriever/review-retriever.service";
import {Review} from "../model/Review";
import {MatTableDataSource} from "@angular/material";

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
  dataSource = new MatTableDataSource();

  constructor(
    private bookSearchService: BookSearchService,
    private location: ActivatedRoute,
    private reviewRetrieverService: ReviewRetrieverService,
    private router: Router,
  ) { }

  ngOnInit() {
    let id = +this.location.snapshot.paramMap.get("id");
    this.book = this.bookSearchService.getBook(id);
    if (this.book == undefined){
      this.router.navigate(["/index"]);
    }
    console.log(id);
    console.log(this.book);
    this.reviewRetrieverService.retrieveReviews(this.book).subscribe(
      resp => {
        console.log(resp);
        this.reviews = resp.body;
        this.dataSource.data = this.reviews;
      },
      error => {
        console.log(error);
      }
    )
  }

}
