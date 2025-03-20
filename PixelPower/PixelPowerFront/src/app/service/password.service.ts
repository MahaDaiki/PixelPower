import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {
  private apiUrl = `http://localhost:8080/api/password`;

  constructor(private http: HttpClient) {}

  sendResetEmail(email: string) {
    return this.http.post('http://localhost:8080/api/password/forgot', { email }, { responseType: 'text' });
  }

  resetPassword(token: string, newPassword: string) {
    return this.http.post('http://localhost:8080/api/password/reset', { token, newPassword }, { responseType: 'text' });
  }


}
