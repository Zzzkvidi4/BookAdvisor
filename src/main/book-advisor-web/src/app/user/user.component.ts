import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user-service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../model/User";
import {Review} from "../model/Review";
import {Book} from "../model/Book";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  private id: number;
  user: User;
  reviews: Review[];
  selectedBook: Book;

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
        this.user = resp.body.data;
      },
      error => {
        this.router.navigate(["accounts"]);
      }
    )
  }

  showReviews(book: Book) {
    this.selectedBook = book;
    this.userService.getReviewsFavourite(this.id, book.bookId).subscribe(
      resp => {
        this.reviews = resp.body.data;
        if (this.reviews == null) {
          this.router.navigate(["index"]);
        }
      }
    )
  }
}
