import {Component, OnInit} from '@angular/core';
import {LoginService} from "./service/loginner/login.service";
import {Router} from "@angular/router";

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

    this.loginService.isLoggedIn().subscribe(
      resp => {
        console.log(resp);
        if (resp.body.data != null) {
          LoginService.username = resp.body.data.username;
          LoginService.userId = resp.body.data.userId;
          LoginService.isAuthorized = true;
          LoginService.logged.emit(true);
        }
      },
      error => {
        console.log(error);
      }
    );
  }
  title = 'app';
  isAuthorized: boolean;
  userId: number;
  username: string;

  constructor(
    private loginService: LoginService,
    private router: Router
  ){}

  logout() {
    this.loginService.logout().subscribe(
      resp => {
        LoginService.isAuthorized = false;
        LoginService.logged.emit(true);
        this.router.navigate(['index']);
      },
      error => {
        console.log(error);
      }
    )
  }
}
