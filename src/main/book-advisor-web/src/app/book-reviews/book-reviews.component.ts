import { Component, OnInit } from '@angular/core';
import {BookSearchService} from "../service/book-searcher/book-search.service";
import {ActivatedRoute} from "@angular/router";
import {Book} from "../model/Book";

@Component({
  selector: 'app-book-reviews',
  templateUrl: './book-reviews.component.html',
  styleUrls: ['./book-reviews.component.css']
})
export class BookReviewsComponent implements OnInit {

  book: Book;

  constructor(private bookSearchService: BookSearchService, private location: ActivatedRoute) { }

  ngOnInit() {
    let id = +this.location.snapshot.paramMap.get("id");
    this.book = this.bookSearchService.getBook(id);
    console.log(id);
    console.log(this.book);
  }

}
