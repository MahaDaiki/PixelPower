import { createFeatureSelector, createSelector } from '@ngrx/store';
import { GameState } from './games.reducer';

export const selectGameState = createFeatureSelector<GameState>('game');


export const selectGameById = (id: number) =>
  createSelector(selectGameState, (state: GameState) =>
    state.games.find((game) => game.id === id)
  );


export const selectGames = createSelector(selectGameState, (state) => state.games);
export const selectGameLoading = createSelector(selectGameState, (state) => state.loading);
export const selectGameError = createSelector(selectGameState, (state) => state.error);

export const selectSearchResults = createSelector(selectGameState, (state) => state.searchResults);
export const selectIsSearching = createSelector(selectGameState, (state) => !!state.searchQuery);

