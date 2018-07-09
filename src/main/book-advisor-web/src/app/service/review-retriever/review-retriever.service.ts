import { Injectable } from '@angular/core';
import {Book} from "../../model/Book";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Review} from "../../model/Review";

@Injectable()
export class ReviewRetrieverService {

  constructor(
    private http: HttpClient
  ) { }

  retrieveReviews(book: Book): Observable<HttpResponse<Review[]>> {
    return this.http.post<Review[]>(
      "http://localhost:8080/reviews",
      book,
      {observe: "response", withCredentials: true}
    );
  }
}
