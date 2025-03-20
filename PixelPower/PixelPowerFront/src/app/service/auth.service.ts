import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {loginFailure, loginSuccess, registerFailure, registerSuccess} from '../store/auth/auth.actions';
import {Router} from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiLoginUrl = 'http://localhost:8080/api/auth/login';
  private apiRegisterUrl = 'http://localhost:8080/api/auth/register';
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient, private store: Store,   private router: Router) {}

  login(email: string, password: string): Observable<any> {
    console.log({ email, password });
    return this.http.post(this.apiLoginUrl, { email, password });

  }

  loginUser(email: string, password: string): void {
    this.login(email, password).subscribe(
      (response) => {
        localStorage.setItem('auth_token', response.token);
        this.store.dispatch(loginSuccess({ token: response.token }));
        this.router.navigate(['/profile']);
      },
      (error) => {
        this.store.dispatch(loginFailure({ error: 'Login failed' }));
      }
    );
  }

  register(username: string, email: string, password: string, profilePicture: File | null): Observable<any> {
    const formData = new FormData();
    formData.append('username', username);
    formData.append('email', email);
    formData.append('password', password);

    if (profilePicture) {
      formData.append('profilePicture', profilePicture);
    }

    return this.http.post(this.apiRegisterUrl, formData);
  }

  registerUser(username: string, email: string, password: string, confirmPassword: string, profilePicture: File | null): void {
    if (password !== confirmPassword) {
      this.store.dispatch(registerFailure({ error: 'Passwords do not match' }));
      console.error('Passwords do not match');
      return;
    }

    console.log('Registering user with data:', { username, email, password, profilePicture });

    this.register(username, email, password, profilePicture).subscribe(
      (response) => {
        console.log('Registration successful:', response);
        this.store.dispatch(registerSuccess({ message: response.message }));
        this.router.navigate(['auth/login']);
      },
      (error) => {
        console.error('Registration failed:', error);
        this.store.dispatch(registerFailure({ error: 'Registration failed' }));
      }
    );
  }


  getUser(): any {
    const token = localStorage.getItem('auth_token');
    if (!token) return null;

    return this.jwtHelper.decodeToken(token);
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('auth_token');
    return token ? !this.jwtHelper.isTokenExpired(token) : false;
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    this.router.navigate(['/auth/login']);
  }

  getUserRole(): string | null {
    const user = this.getUser();
    console.log(user)
    return user ? user.role : null;
  }




}
