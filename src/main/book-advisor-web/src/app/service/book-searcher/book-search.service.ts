import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Book} from "../../model/Book";
import "rxjs/add/observable/of";

@Injectable()
export class BookSearchService {

  private static books: Book[] = [];

  constructor(private http: HttpClient) {
    console.log("BookSearcher created!");
  }

  getBooks(query: string): Observable<HttpResponse<Book[]>> {
    query = query.split(" ").join("+");
    let response = this.http.get<Book[]>(
      "http://localhost:8080/books_search?query=" + query,
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
