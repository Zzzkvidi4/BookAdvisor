import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {BookSearchService} from "../service/book-searcher/book-search.service";
import {Book} from "../model/Book";
import {MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {SearchQuery} from "../model/SearchQuery";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit, AfterViewInit {

  query: string = "";
  useLitres: boolean = true;
  useOzon: boolean = true;
  books: Book[];
  isQuering: boolean = false;
  isReady: boolean = false;
  isError: boolean = false;
  displayedColumns = ["position", "author", "title"];
  dataSource = new MatTableDataSource();

  constructor(private bookSearchService: BookSearchService, private router: Router) { }

  //@ViewChild("paginator") paginator: MatPaginator;

  ngOnInit() {
    console.log("Initialized BookList");
    console.log(BookSearchService.books);
    if (BookSearchService.books != null) {
      this.books = BookSearchService.books;
      this.dataSource.data = this.books;
      this.isQuering = false;
      this.isReady = true;
      this.query = BookSearchService.selector;
    }
  }

  ngAfterViewInit() {
    //this.dataSource.paginator = this.paginator;
  }

  onSearch(){
    this.query = this.query.trim();
    this.isQuering = true;
    this.isReady = false;
    if (this.query != "") {
      let queryParams = new SearchQuery();
      queryParams.selector = this.query;
      if (this.useLitres) queryParams.resources.push("LITRES");
      if (this.useOzon) queryParams.resources.push("OZON");
      this.bookSearchService.getBooks(queryParams).subscribe(
        resp => {
          console.log(resp);
          this.books = resp.body;
          this.bookSearchService.setBooks(resp.body);
          BookSearchService.selector = this.query;
          console.log(BookSearchService.selector);
          console.log(this.books);
          this.dataSource.data = resp.body;
          this.isQuering = false;
          this.isReady = true;
          this.isError = false;
        },
        error => {
          console.log(error);
          this.isQuering = false;
          this.isReady = true;
          this.isError = true;
        }
      )
    }
  }

  clear(){
    this.query = "";
  }

  onRowClick(row) {
    console.log(row);
    let id = this.books.indexOf(row) + 1;
    this.router.navigate(["/reviews/" + id]);
  }
}
