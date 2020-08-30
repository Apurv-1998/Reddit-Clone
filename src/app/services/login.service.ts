import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Login } from '../common/login';
import { Observable } from 'rxjs';
import { LoginResponse } from 'src/app/common/login-response';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  baseUrl: string = 'http://localhost:8080/reddit-clone-app/api';

  constructor(private httpClient: HttpClient) { }

  loginUser(loginPayload: Login): Observable<any>{

    const searchUrl = `${this.baseUrl}/login`;

    return this.httpClient.post<LoginResponse>(searchUrl,loginPayload);

  }
}
