import { createFeatureSelector, createSelector } from '@ngrx/store';
import { GameState } from './games.reducer';

export const selectGameState = createFeatureSelector<GameState>('game');

export const selectGames = createSelector(selectGameState, (state) => state.games);
export const selectGameLoading = createSelector(selectGameState, (state) => state.loading);
export const selectGameError = createSelector(selectGameState, (state) => state.error);
