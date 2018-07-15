import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {LoginInfo} from "../../model/LoginInfo";
import {ResponseContainer} from "../../model/ResponseContainer";
import {User} from "../../model/User";

@Injectable()
export class LoginService {
  public static isAuthorized: boolean = false;
  public static userId: number;
  public static username: string;

  constructor(private http: HttpClient) { }

  login(loginInfo: LoginInfo): Observable<HttpResponse<User>> {
    return this.http.post<any>("http://localhost:8080/login", loginInfo, {observe: "response", withCredentials: true});
  }

  logout(): Observable<HttpResponse<any>> {
    return this.http.get<any>("http://localhost:8080/logout", {observe: "response", withCredentials: true});
  }

  register(loginInfo: LoginInfo): Observable<HttpResponse<ResponseContainer<boolean>>> {
    return this.http.post<ResponseContainer<boolean>>("http://localhost:8080/users", loginInfo, {observe: "response", withCredentials: true});
  }

  checkLogin(login: string): Observable<HttpResponse<ResponseContainer<boolean>>> {
    return this.http.get<ResponseContainer<boolean>>("http://localhost:8080/users/check-login?login=" + login, {observe: "response", withCredentials: true});
  }
}
