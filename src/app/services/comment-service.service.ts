import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateComment } from '../common/create-comment';

@Injectable({
  providedIn: 'root'
})
export class CommentServiceService {

  baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { }

  createComment(postId: string,userId: string,commentPayload: CreateComment): Observable<any> {

    const searchUrl = `${this.baseUrl}/comments/${userId}/${postId}/createComments`;

    return this.httpClient.post(searchUrl,commentPayload,{responseType: 'text'});

  }
}
