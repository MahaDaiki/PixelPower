import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GamecomparaisonService {
  private apiUrl = 'http://localhost:8080/api/game-comparison/compare';

  constructor(private http: HttpClient) {}

  compareGame(appId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${appId}`);
  }
}
