import {Component, Input, OnInit} from '@angular/core';
import {Review} from "../model/Review";

@Component({
  selector: 'app-review-list',
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.css']
})
export class ReviewListComponent implements OnInit {
  @Input() isQuering: boolean;
  @Input() isReady: boolean;
  @Input() isSuccess: boolean;
  @Input() isError: boolean;
  @Input() reviews: Review[];

  constructor() { }

  ngOnInit() {
  }

}
