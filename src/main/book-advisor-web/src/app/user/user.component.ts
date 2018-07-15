import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user-service/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  private id: number;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get("id");
    this.userService.getUser(this.id).subscribe(
      resp => {
        console.log(resp);
        if (resp.body.data == null) {
          this.router.navigate(["index"]);
        }
      },
      error => {
        this.router.navigate(["accounts"]);
      }
    )
  }

}
