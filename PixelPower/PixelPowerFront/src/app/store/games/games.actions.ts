import { createAction, props } from '@ngrx/store';

export const loadGames = createAction('[Game] Load Games', props<{ page: number; size: number }>());
export const loadGamesSuccess = createAction('[Game] Load Games Success', props<{ games: any[] }>());
export const loadGamesFailure = createAction('[Game] Load Games Failure', props<{ error: string }>());


export const loadGame = createAction('[Game] Load Game', props<{ id: number }>());
export const loadGameSuccess = createAction('[Game] Load Game Success', props<{ game: any }>());
export const loadGameFailure = createAction('[Game] Load Game Failure', props<{ error: string }>());
