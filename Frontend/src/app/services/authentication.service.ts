import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {UserLoginResponse} from '../models/user-login-response';


const config = {apiUrl: 'http://localhost:8080'};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<UserLoginResponse>;
  public currentUser: Observable<UserLoginResponse>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<UserLoginResponse>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): UserLoginResponse {
    return this.currentUserSubject.value;
  }

  login(username, password) {
    return this.http.post<any>(
      `${config.apiUrl}/login`,
      {username, password}
      )
      .pipe(
        map(userLoginResponse => {
          // login successful if there's a jwt token in the response
          if (userLoginResponse && userLoginResponse.jtwToken) {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('currentUser', JSON.stringify(userLoginResponse));
            this.currentUserSubject.next(userLoginResponse);
          }
          return userLoginResponse;
        })
      );
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
