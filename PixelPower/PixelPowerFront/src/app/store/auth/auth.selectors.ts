import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AuthState} from './auth.reducer';


const selectAuthState = createFeatureSelector<AuthState>('auth');

export const selectAuthToken = createSelector(
  selectAuthState,
  (state: AuthState) => state.token
);

export const selectAuthMessage = createSelector(
  selectAuthState,
  (state: AuthState) => state.message
);


export const selectAuthError = createSelector(
  selectAuthState,
  (state: AuthState) => state.error
);
