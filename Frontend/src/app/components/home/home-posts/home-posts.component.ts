import { Component, OnInit } from '@angular/core';
import {Post} from '../../../models/post';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-home-posts',
  templateUrl: './home-posts.component.html',
  styleUrls: ['./home-posts.component.css']
})
export class HomePostsComponent implements OnInit {
  public posts: Post[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<Post[]>('http://localhost:8080/api/posts')
      .subscribe(posts => this.posts = posts);
  }

}
