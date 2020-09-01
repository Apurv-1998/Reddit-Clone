import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/common/post';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;

  //Taking the PostRest fro Post-tile to vote-button
  @Input() post: Post;

  constructor() { }

  ngOnInit(): void {
  }

}
