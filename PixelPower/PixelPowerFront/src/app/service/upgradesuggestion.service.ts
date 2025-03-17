import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpgradesuggestionService {

  private apiUrl = 'http://localhost:8080/api/upgrade-suggestions';

  constructor(private http: HttpClient) {}

  getAllSuggestions(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }
  getSuggestionsByComparisonId(comparisonId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${comparisonId}`);
  }

  generateSuggestions(comparisonId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/generate/${comparisonId}`);
  }
}
