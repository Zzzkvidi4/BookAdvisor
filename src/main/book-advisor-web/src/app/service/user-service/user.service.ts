import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "../../model/User";
import {ResponseContainer} from "../../model/ResponseContainer";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<HttpResponse<ResponseContainer<User>>> {
    return this.http.get<ResponseContainer<User>>("http://localhost:8080/users/"+id, {observe: "response", withCredentials: true});
  }
}
