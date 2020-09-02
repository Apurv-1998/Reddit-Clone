import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubredditRest } from '../common/subreddit-rest';
import { UserId } from 'src/app/common/user-id';
import { CreateSubredditComponent } from '../components/create-subreddit/create-subreddit.component';
import { CreateSubreddit } from '../common/create-subreddit';

@Injectable({
  providedIn: 'root'
})
export class SubredditRestService {

  private baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { 

  }

  getAllSubreddits(): Observable<any>{

    const searchUrl = `${this.baseUrl}/subreddits/showAll`;

    return this.httpClient.get<SubredditRest>(searchUrl);

  }

  getSubredditUserId(): Observable<UserId>{

    const searchUrl = `${this.baseUrl}/subreddits/getUserId`;

    return this.httpClient.get<UserId>(searchUrl);

  }

  createNewSubreddit(payload: CreateSubreddit,userId: string): Observable<any>{

    const searchUrl = `${this.baseUrl}/subreddits/${userId}/createSubreddits`;

    return this.httpClient.post(searchUrl,payload,{responseType: 'text'});

  }
}
