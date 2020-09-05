import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../common/post';
import { CreatePostDetails } from '../common/create-post-details';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { }

  getPostsOfUser(userId: string): Observable<any>{

    const searchUrl = `${this.baseUrl}/posts/${userId}/showPosts`;

    return this.httpClient.get<Post>(searchUrl);

  }

  createNewPost(createPostpayload: CreatePostDetails): Observable<any> {

    const searchUrl = `${this.baseUrl}/posts/createPosts`;

    return this.httpClient.post(searchUrl,createPostpayload);

  }

  getSpecificPostDetails(postId: string): Observable<any>{

    const searchUrl = `${this.baseUrl}/posts/${postId}/displayPost`;

    return this.httpClient.get<Post>(searchUrl);

  }
}
