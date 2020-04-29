import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../../models/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  public users: User[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<User[]>('http://localhost:8080/api/users')
      .subscribe(users => this.users = users);
  }

}
