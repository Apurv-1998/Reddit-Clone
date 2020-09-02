import { Component, OnInit } from '@angular/core';
import { SubredditRest } from 'src/app/common/subreddit-rest';
import { SubredditRestService } from 'src/app/services/subreddit-rest.service';

@Component({
  selector: 'app-show-subreddits',
  templateUrl: './show-subreddits.component.html',
  styleUrls: ['./show-subreddits.component.css']
})
export class ShowSubredditsComponent implements OnInit {

  //Getting the subreddit response
  subredditRest: SubredditRest[] = [];

  constructor(private subredditService: SubredditRestService) { }

  ngOnInit(): void {

    this.subredditService.getAllSubreddits().subscribe(
      data => {
        this.subredditRest = data;
      }
    );

  }

}
