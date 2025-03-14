import { createReducer, on } from '@ngrx/store';
import {
  loadGames,
  loadGamesSuccess,
  loadGamesFailure,
  loadGame,
  loadGameSuccess,
  loadGameFailure, searchGames, searchGamesSuccess, searchGamesFailure
} from './games.actions';

export interface GameState {
  games: any[];
  loading: boolean;
  searchResults: any[];
  searchQuery: string | null;
  error: string | null;
}

const initialState: GameState = {
  games: [],
  searchResults: [],
  searchQuery: null,
  loading: false,
  error: null,
};

export const gameReducer = createReducer(
  initialState,
  on(loadGames, (state) => ({ ...state, loading: true, error: null })),
  on(loadGamesSuccess, (state, { games }) => ({ ...state, games, loading: false })),
  on(loadGamesFailure, (state, { error }) => ({ ...state, error, loading: false })),

  on(loadGame, (state) => ({ ...state, loading: true, error: null })),
  on(loadGameSuccess, (state, { game }) => ({
    ...state,
    games: [...state.games, game],
    loading: false,
  })),
  on(loadGameFailure, (state, { error }) => ({ ...state, error, loading: false })),

  on(searchGames, (state, { name }) => ({
    ...state,
    searchQuery: name,
    isSearching: true,
    loading: true
  })),
  on(searchGamesSuccess, (state, { games }) => ({
    ...state,
    searchResults: games,
    loading: false,
    isSearching: false
  })),
  on(searchGamesFailure, (state, { error }) => ({
    ...state,
    searchResults: [],
    isSearching: false,
    loading: false,
    error
  }))
);
