import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {
  private apireviewsUrl = 'http://localhost:8080/api/reviews';
  constructor(private http: HttpClient) {}


  createReview(review: any): Observable<any> {
    return this.http.post<any>(`${this.apireviewsUrl}`, review);
  }


  getReviewsByGameName(gameName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apireviewsUrl}/reviews/game/${gameName}`);
  }
}
