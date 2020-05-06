import {Component, Input, OnInit} from '@angular/core';
import {Group} from '../../../models/group';

@Component({
  selector: 'app-home-group',
  templateUrl: './home-group.component.html',
  styleUrls: ['./home-group.component.css']
})
export class HomeGroupComponent implements OnInit {

  @Input() group: Group;
  public groupImage: any;

  constructor() { }

  ngOnInit(): void {
    if (this.group.groupPicture) {
      this.groupImage = 'data:' + this.group.groupPicture.type + ';base64,' + this.group.groupPicture.picByte;
      // 'data:' + response.type + ';base64,' + response.picByte;
      // console.log(this.groupImage);
    } else {
      this.groupImage = null;
    }
  }
}
