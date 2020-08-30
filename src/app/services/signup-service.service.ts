import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupUser } from '../common/signup-user';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SignupServiceService {

  baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { }

/* Signup Method Service */  
  signUpUser(signUpRequestPayload: SignupUser): Observable<any> {

    const searchUrl = `${this.baseUrl}/users/signup`;

    return this.httpClient.post(searchUrl,signUpRequestPayload,{responseType: 'text'});

  }
}
