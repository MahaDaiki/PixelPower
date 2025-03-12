import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private apiProfileUrl = 'http://localhost:8080/api/users/me';
  private apiAvatarUrl = 'http://localhost:8080/api/users/me/profile-picture';


  constructor(private http: HttpClient) {}

  getProfile(): Observable<any> {
    return this.http.get<any>(this.apiProfileUrl);
  }

  updateProfile(data: { email: string; username:string }): Observable<any>{
    return this.http.put<any>(this.apiProfileUrl, data);
  }

  updateAvatar(formData: FormData): Observable<any> {
    return this.http.put<any>(this.apiAvatarUrl, formData);
  }


}
