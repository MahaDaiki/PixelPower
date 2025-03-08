import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { GamesService } from '../../service/games.service';
import { loadGames, loadGamesSuccess, loadGamesFailure } from './games.actions';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class GameEffects {
  loadGames$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadGames),
      mergeMap((action) =>
        this.gameService.getGames(action.page, action.size).pipe(
          map((data) => loadGamesSuccess({ games: data })),
          catchError((error) => of(loadGamesFailure({ error: error.message })))
        )
      )
    )
  );

  constructor(private actions$: Actions, private gameService: GamesService) {}
}
