import { createAction, props } from '@ngrx/store';

export const loadGames = createAction('[Game] Load Games', props<{ page: number; size: number }>());
export const loadGamesSuccess = createAction('[Game] Load Games Success', props<{ games: any[] }>());
export const loadGamesFailure = createAction('[Game] Load Games Failure', props<{ error: string }>());
