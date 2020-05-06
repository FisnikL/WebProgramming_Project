import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {GroupDetails} from '../../models/group-details';
import {GroupPicture} from '../../models/group-picture';
import {GroupDetailsPost} from '../../models/group-details-post';

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.css']
})
export class GroupDetailsComponent implements OnInit {

  public groupCode: string;
  public groupDetails: GroupDetails;
  public groupImage: any;
  public groupPosts: GroupDetailsPost[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      params => {
        this.groupCode = params.get('code');
        this.getGroupDetails();
        this.getGroupPicture();
        this.getGroupPosts();
      }
    );
  }

  getGroupDetails() {
    this.http.get<GroupDetails>(`http://localhost:8080/api/groups/details/${this.groupCode}`)
      .subscribe(
        result => {
          // console.log('RESULT:');
          // console.log(result);
          this.groupDetails = result;
          // this.groupImage = 'data:image/jpeg;base64,' + result.groupPicture.picByte;
        }
      );
  }

  getGroupPicture() {
    this.http.get<GroupPicture>(`http://localhost:8080/api/groups/details/${this.groupCode}/groupImage`)
      .subscribe(
        response => {
          if (response.picByte !== null) {
            this.groupImage = 'data:' + response.type + ';base64,' + response.picByte;
            // console.log(this.groupImage);
          } else {
            this.groupImage = null;
          }
        }
      );
  }

  getGroupPosts() {
    this.http.get<GroupDetailsPost[]>(`http://localhost:8080/api/posts/posts-from-group?groupCode=${this.groupCode}`)
      .subscribe(
        response => {
          this.groupPosts = response;
        }
      );
  }

  imageUploaded($event: boolean) {
    this.getGroupPicture();
  }
}
