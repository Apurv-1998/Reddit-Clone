import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubredditRest } from '../common/subreddit-rest';

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
}
