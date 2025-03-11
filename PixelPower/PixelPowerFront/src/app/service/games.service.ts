import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, tap, throwError} from 'rxjs';
import {
  loadGame,
  loadGameFailure,
  loadGames,
  loadGamesFailure,
  loadGamesSuccess,
  loadGameSuccess
} from '../store/games/games.actions';
import {selectGameById, selectGameError, selectGameLoading, selectGames} from '../store/games/games.selectors';
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


  getGames(page: number, size: number): Observable<any[]> {
    this.store.dispatch(loadGames({ page, size }));
    const url = `${this.apiUrl}?page=${page}&size=${size}`;
    console.log('Fetching games from:', url);

    return this.http.get<any[]>(url).pipe(
      tap(games => {
        console.log('Received data:', games);
        this.store.dispatch(loadGamesSuccess({ games }));
      }),
      catchError(error => {
        console.error('API Error:', error);
        this.store.dispatch(loadGamesFailure({ error: error.message || 'Unknown error' }));
        return throwError(() => error);
      })
    );
  }

  getGameById(id: number): Observable<any> {
    console.log('Service getGameById called for ID:', id);
    this.store.dispatch(loadGame({ id }));

    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      tap(game => {
        console.log('Received game data:', game);
        this.store.dispatch(loadGameSuccess({ game }));
      }),
      catchError((error) => {
        console.error('Error fetching game by ID:', error);
        this.store.dispatch(loadGameFailure({ error: error.message || 'Unknown error' }));
        return throwError(() => error);
      })
    );
  }

  getGamesSelector(): Observable<any[]> {
    return this.store.select(selectGames);
  }
  getGameByIdSelector(id: number): Observable<any> {
    return this.store.select(selectGameById(id));
  }

  getLoadingSelector(): Observable<boolean> {
    return this.store.select(selectGameLoading);
  }

  getErrorSelector(): Observable<string | null> {
    return this.store.select(selectGameError);
  }



}
