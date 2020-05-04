import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../../models/post';

@Component({
  selector: 'app-home-post',
  templateUrl: './home-post.component.html',
  styleUrls: ['./home-post.component.css']
})
export class HomePostComponent implements OnInit {
  @Input() post: Post;
  public isThumbUped = false;
  public isThumbDowned = false;

  constructor() { }

  ngOnInit(): void {
  }

  thumbUpClicked() {
    // console.log('thumbUpClicked!');
    this.isThumbUped = !this.isThumbUped;
    if (this.isThumbUped === true) {
      this.post.numOfThumbUps++;

      if (this.isThumbDowned === true) {
        this.isThumbDowned = false;
        this.post.numOfThumbDowns--;
      }
    } else {
      this.post.numOfThumbUps--;
    }
  }

  thumbDownClicked() {
    // console.log('thumbDownClicked');
    this.isThumbDowned = !this.isThumbDowned;
    if (this.isThumbDowned === true) {
      this.post.numOfThumbDowns++;

      if (this.isThumbUped === true) {
        this.isThumbUped = false;
        this.post.numOfThumbUps--;
      }
    } else {
      this.post.numOfThumbDowns--;
    }
  }
}
