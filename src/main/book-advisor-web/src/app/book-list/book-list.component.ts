import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {BookSearchService} from "../service/book-searcher/book-search.service";
import {Book} from "../model/Book";
import {MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {SearchResult} from "../model/SearchRequest";

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
      let queryParams = new SearchResult();
      queryParams.selector = this.query;
      queryParams.useLitres = this.useLitres;
      queryParams.useOzon = this.useOzon;
      this.bookSearchService.getBooks(queryParams).subscribe(
        resp => {
          console.log(resp);
          this.books = resp.body;
          this.bookSearchService.setBooks(resp.body);
          console.log(this.books);
          this.dataSource.data = resp.body;
          this.isQuering = false;
          this.isReady = true;
        },
        error => {
          console.log(error);
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
