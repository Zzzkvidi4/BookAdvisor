import {Component, OnInit} from '@angular/core';
import {LoginService} from "./service/loginner/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
    console.log("Initialized app-root");
    LoginService.logged.subscribe((next) => {
      console.log("Get next emitted value!");
      this.isAuthorized = LoginService.isAuthorized;
      this.userId = LoginService.userId;
      this.username = LoginService.username;
    });
  }
  title = 'app';
  isAuthorized: boolean;
  userId: number;
  username: string;

  constructor(
    private loginService: LoginService
  ){}

  logout() {
    this.loginService.logout().subscribe(
      resp => {
        LoginService.isAuthorized = false;
        LoginService.logged.emit(true);
      },
      error => {
        console.log(error);
      }
    )
  }
}
