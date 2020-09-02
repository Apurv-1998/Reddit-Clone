import { Component, OnInit } from '@angular/core';
import { SubredditRest } from 'src/app/common/subreddit-rest';
import { ActivatedRoute, Router } from '@angular/router';
import { SubredditRestService } from 'src/app/services/subreddit-rest.service';
import { SideBarComponent } from '../side-bar/side-bar.component';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {

  //Subreddit Array Response Received
  subredditResponse: SubredditRest[] = [];


  //Display All View -> If the number of subreddits > 4 than only display 3
  showDisplayAll: boolean;


  constructor(private subredditService: SubredditRestService) {
   }



  ngOnInit(): void {

    this.listOfSubreddits();

  }

  listOfSubreddits(){
    this.subredditService.getAllSubreddits().subscribe(
      data => {
        console.log('Subreddit Data '+JSON.stringify(data));
        
        if(data.length > 3){
          this.subredditResponse = data.splice(0,3);
          this.showDisplayAll = true;
        }
        else{
          this.subredditResponse = data;
        }
  
      }
    );
  }

}
