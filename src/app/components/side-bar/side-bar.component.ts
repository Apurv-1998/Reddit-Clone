import { Component, OnInit } from '@angular/core';
import { SubredditRestService } from 'src/app/services/subreddit-rest.service';
import { UserId } from 'src/app/common/user-id';
import { Router } from '@angular/router';


@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})

export class SideBarComponent implements OnInit {

  //Getting the userId for subreddit
  userIdRest: UserId;

  constructor(private subredditService: SubredditRestService,
              private router: Router) { }

  ngOnInit(): void {

  }

  getTheUserId() {
    this.subredditService.getSubredditUserId().subscribe(
      data => {
        this.userIdRest = data;
        this.router.navigate(['/createSubreddit/'+this.userIdRest.userId]);
      }
    );
  }

}
