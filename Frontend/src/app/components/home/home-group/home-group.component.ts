import {Component, Input, OnInit} from '@angular/core';
import {Group} from '../../../models/group';

@Component({
  selector: 'app-home-group',
  templateUrl: './home-group.component.html',
  styleUrls: ['./home-group.component.css']
})
export class HomeGroupComponent implements OnInit {

  @Input() group: Group;

  constructor() { }

  ngOnInit(): void {
  }

}
