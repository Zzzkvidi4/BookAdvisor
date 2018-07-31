import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {LoginInfo} from '../model/LoginInfo';
import {LoginService} from '../service/loginner/login.service';
import {FormControl, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {
  login: string;
  password: string;
  wrongCredentials = false;

  loginFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(5),
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(5)
  ]);

  constructor(
    private loginService: LoginService,
    private router: Router,
    @Input() private dialogRef: MatDialogRef<LoginDialogComponent>
  ) { }

  ngOnInit() {
  }

  onAuthorize() {
    let loginInfo = new LoginInfo();
    loginInfo.username = this.login;
    loginInfo.password = this.password;
    this.loginService.login(loginInfo).subscribe(
      resp => {
        this.wrongCredentials = false;
        console.log(resp);
        LoginService.isAuthorized = true;
        LoginService.userId = resp.body.data.userId;
        LoginService.username = resp.body.data.username;
        LoginService.logged.emit(true);
        console.log(LoginService.username);
        this.dialogRef.close();
      },
      error => {
        console.log(error);
        this.wrongCredentials = true;
      }
    );
  }

  onRegister() {
    this.router.navigate(["registration"]);
    this.dialogRef.close();
  }
}
