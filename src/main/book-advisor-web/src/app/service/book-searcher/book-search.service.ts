import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Book} from "../../model/Book";
import "rxjs/add/observable/of";
import {SearchQuery} from "../../model/SearchQuery";

@Injectable()
export class BookSearchService {

  static books: Book[] = null;

  constructor(private http: HttpClient) {
    console.log("BookSearcher created!");
  }

  getBooks(query: SearchQuery): Observable<HttpResponse<Book[]>> {
    let response = this.http.post<Book[]>(
      "http://localhost:8080/books",
      query,
      {withCredentials: true, observe: "response"}
    );

    return response;
  }

  setBooks(books: Book[]){
    BookSearchService.books = books;
    console.log(books);
  }

  getBook(id: number): Book {
    console.log("From getBook:");
    console.log(BookSearchService.books);
    if (BookSearchService.books != null && BookSearchService.books.length >= id) {
      return BookSearchService.books[id - 1];
    }
    return null;
  }
}
