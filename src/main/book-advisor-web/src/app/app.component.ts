import { Component } from '@angular/core';
import {LoginService} from "./service/loginner/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  isAuthorized: boolean;
  userId: number;
  username: string;

  constructor(
    private loginService: LoginService
  ){
    this.isAuthorized = LoginService.isAuthorized;
    this.userId = LoginService.userId;
    this.username = LoginService.username;
  }

  logout() {
    this.loginService.logout().subscribe(
      resp => {
        LoginService.isAuthorized = false;
      },
      error => {

      }
    )
  }
}
