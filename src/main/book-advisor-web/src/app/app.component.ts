import {Component, EventEmitter, OnInit} from '@angular/core';
import {LoginService} from "./service/loginner/login.service";
import {Router} from "@angular/router";
import {MatDialog, MatDialogRef} from '@angular/material';
import {LoginDialogComponent} from './login-dialog/login-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  static loginEmitter: EventEmitter<boolean> = new EventEmitter();
  appName = 'BookAdvisor';
  isAuthorized: boolean;
  userId: number;
  username: string;

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

  constructor(
    private loginService: LoginService,
    private router: Router,
    private dialog: MatDialog,
  ){
    AppComponent.loginEmitter.subscribe(
      value => {
        this.openLoginDialog();
      }
    );
  }

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

  openLoginDialog() {
    const dialogRef = this.dialog.open(LoginDialogComponent, {
      height: '350px',
    });

    dialogRef.afterClosed().subscribe(
      result => {
        console.log(result);
      }
    );
  }
}
