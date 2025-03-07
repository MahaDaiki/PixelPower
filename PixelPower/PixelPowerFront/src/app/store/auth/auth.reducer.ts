import { createReducer, on } from '@ngrx/store';
import {loginSuccess, loginFailure, registerSuccess, registerFailure} from './auth.actions';

export interface AuthState {
  token: string | null;
  error: string | null;
  message: string | null;
}

export const initialState: AuthState = {
  token: null,
  error: null,
  message: null,
};

export const authReducer = createReducer(
  initialState,
  //Login
  on(loginSuccess, (state, { token }) => ({
    ...state,
    token,
    error: null,
  })),
  on(loginFailure, (state, { error }) => ({
    ...state,
    error,

  })),

  //register
  on(registerSuccess, (state, { message }) => ({
    ...state,
    message,
    error: null,
  })),
  on(registerFailure, (state, { error }) => ({
    ...state,
    error,
    message: null,
  }))

);

