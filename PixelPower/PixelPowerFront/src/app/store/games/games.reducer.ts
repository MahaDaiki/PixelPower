import { createReducer, on } from '@ngrx/store';
import {
  loadGames,
  loadGamesSuccess,
  loadGamesFailure,
  loadGame,
  loadGameSuccess,
  loadGameFailure
} from './games.actions';

export interface GameState {
  games: any[];
  loading: boolean;
  error: string | null;
}

const initialState: GameState = {
  games: [],
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
  on(loadGameFailure, (state, { error }) => ({ ...state, error, loading: false }))
);
