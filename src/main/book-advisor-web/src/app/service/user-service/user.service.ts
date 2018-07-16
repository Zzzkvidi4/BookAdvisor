import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "../../model/User";
import {ResponseContainer} from "../../model/ResponseContainer";
import {Book} from "../../model/Book";
import {Review} from "../../model/Review";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<HttpResponse<ResponseContainer<User>>> {
    return this.http.get<ResponseContainer<User>>("http://localhost:8080/users/"+id, {observe: "response", withCredentials: true});
  }

  addToFavourite(id: number, book: Book): Observable<HttpResponse<ResponseContainer<boolean>>> {
    return this.http.post<any>("http://localhost:8080/users/" + id + "/favourites", book, {observe: "response", withCredentials: true});
  }

  isInFavourite(id: number, book: Book): Observable<HttpResponse<ResponseContainer<boolean>>> {
    return this.http.post<ResponseContainer<boolean>>("http://localhost:8080/users/" + id + "/favourites/is-in", book, {observe: "response", withCredentials: true});
  }

  getReviewsFavourite(userId: number, bookId: number): Observable<HttpResponse<ResponseContainer<Review[]>>> {
    return this.http.get<ResponseContainer<Review[]>>("http://localhost:8080/users/" + userId + "/favourites/" + bookId, {observe: "response", withCredentials: true});
  }
}
