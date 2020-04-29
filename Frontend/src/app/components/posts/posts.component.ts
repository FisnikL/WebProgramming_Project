import { Component, OnInit } from '@angular/core';
import {User} from '../../models/user';
import {HttpClient} from '@angular/common/http';
import {Post} from '../../models/post';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  public posts: Post[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<Post[]>('http://localhost:8080/api/posts')
      .subscribe(posts => this.posts = posts);
  }
}
