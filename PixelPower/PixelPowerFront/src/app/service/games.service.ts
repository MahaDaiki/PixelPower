import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, tap, throwError} from 'rxjs';
import {loadGames, loadGamesFailure, loadGamesSuccess} from '../store/games/games.actions';
import {selectGameError, selectGameLoading, selectGames} from '../store/games/games.selectors';
import {Store} from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class GamesService {
  private apiUrl = 'http://localhost:8080/api/games';
  constructor(
    private http: HttpClient,
    private store: Store
  ) {}

  // Method to get games data from the API and update the store
  getGames(page: number, size: number): void {
    // Dispatch loading action
    this.store.dispatch(loadGames({ page, size }));

    const url = `${this.apiUrl}?page=${page}&size=${size}`;
    console.log('Fetching games from:', url);

    this.http.get<any>(url).pipe(
      tap(data => console.log('Received data:', data)),
      catchError(error => {
        console.error('API Error:', error);
        this.store.dispatch(loadGamesFailure({ error: error.message || 'Unknown error' }));
        return throwError(() => error);
      })
    ).subscribe({
      next: (games) => {
        this.store.dispatch(loadGamesSuccess({ games }));
      },
      error: (error) => {
        // Error already handled in catchError
      }
    });
  }

  // Selectors as observables for the component to use
  getGamesSelector(): Observable<any[]> {
    return this.store.select(selectGames);
  }

  getLoadingSelector(): Observable<boolean> {
    return this.store.select(selectGameLoading);
  }

  getErrorSelector(): Observable<string | null> {
    return this.store.select(selectGameError);
  }
}
