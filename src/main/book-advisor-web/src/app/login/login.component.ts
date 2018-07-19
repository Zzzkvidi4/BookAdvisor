import {Component, OnInit, ViewChild} from '@angular/core';
import {ErrorStateMatcher, MatDialog, MatTabGroup} from "@angular/material";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {LoginService} from "../service/loginner/login.service";
import {LoginInfo} from "../model/LoginInfo";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  ngOnInit() {
  }

  login: string = "";
  password: string = "";
  wrongCredentials: boolean = false;
  nameIsNotUnique: boolean = false;
  passwordsAreNotEqual: boolean = false;
  passwordConfirm: string = "";

  loginFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(5),
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(5)
  ]);

  passwordConfirmFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(5)
  ]);

  matcher = new MyErrorStateMatcher();

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  checkPasswordEqual() {
    console.log(this.passwordsAreNotEqual);
    this.passwordsAreNotEqual = this.password != this.passwordConfirm;
  }

  checkLoginUnique(){
    this.loginService.checkLogin(this.login).subscribe(
      resp => {
        console.log(resp);
        this.nameIsNotUnique = !resp.body.data;
      },
      error => {
        console.log(error);
      }
    )
  }

  onRegister() {
    let loginInfo = new LoginInfo();
    loginInfo.username = this.login;
    loginInfo.password = this.password;
    this.loginService.register(loginInfo).subscribe(
      resp => {
        console.log(resp);
        this.nameIsNotUnique = !resp.body.data;
        if (!this.nameIsNotUnique) {
          this.router.navigate(["index"]);
        }
      },
      error => {
        console.log(error);
      }
    )
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
        this.router.navigate(["index"]);
      },
      error => {
        console.log(error);
        this.wrongCredentials = true;
      }
    );
  }
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
