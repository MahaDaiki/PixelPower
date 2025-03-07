import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {loginFailure, loginSuccess} from '../store/auth/auth.actions';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiLoginUrl = 'http://localhost:8080/api/auth/login';

  constructor(private http: HttpClient, private store: Store,   private router: Router) {}

  login(email: string, password: string): Observable<any> {
    console.log({ email, password });
    return this.http.post(this.apiLoginUrl, { email, password });

  }

  loginUser(email: string, password: string): void {
    this.login(email, password).subscribe(
      (response) => {
        this.store.dispatch(loginSuccess({ token: response.token }));

        this.router.navigate(['/']);
      },
      (error) => {
        this.store.dispatch(loginFailure({ error: 'Login failed' }));
      }
    );
  }
}
