import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Group} from '../../../models/group';
import {User} from '../../../models/user';

@Component({
  selector: 'app-home-groups',
  templateUrl: './home-groups.component.html',
  styleUrls: ['./home-groups.component.css']
})
export class HomeGroupsComponent implements OnInit {
  public groups: Group[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<Group[]>('http://localhost:8080/api/groups')
      .subscribe(groups => this.groups = groups);
  }

}
