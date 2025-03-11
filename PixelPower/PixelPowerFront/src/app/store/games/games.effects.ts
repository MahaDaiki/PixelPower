import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { GamesService } from '../../service/games.service';
import {
  loadGames,
  loadGamesSuccess,
  loadGamesFailure,
  loadGameSuccess,
  loadGameFailure,
  loadGame
} from './games.actions';
import { catchError, map, mergeMap } from 'rxjs/operators';
import {of, switchMap} from 'rxjs';

@Injectable()
export class GameEffects {


  constructor(private actions$: Actions, private gameService: GamesService) {}

  loadGames$ = createEffect(() =>
      this.actions$.pipe(
        ofType(loadGames),
        switchMap((action) => {
          return this.gameService.getGames(action.page, action.size).pipe(
            map(() => ({ type: '[Games Effect] Games Loaded' })),
            catchError((error) => of(loadGamesFailure({ error: error.message })))
          );
        })
      ),
    { dispatch: false }
  );

  loadGame$ = createEffect(() =>
      this.actions$.pipe(
        ofType(loadGame),
        switchMap((action) => {
          return this.gameService.getGameById(action.id).pipe(
            map(() => ({ type: '[Games Effect] Game Loaded' })),
            catchError((error) => of(loadGameFailure({ error: error.message })))
          );
        })
      ),
    { dispatch: false }
  );
}
