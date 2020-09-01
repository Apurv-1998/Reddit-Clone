import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../common/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { }

  getPostsOfUser(userId): Observable<any>{

    const searchUrl = `${this.baseUrl}/posts/${userId}/showPosts`;

    return this.httpClient.get<Post>(searchUrl);

  }
}
