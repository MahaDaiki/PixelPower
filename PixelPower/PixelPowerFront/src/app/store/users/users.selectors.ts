import { createSelector, createFeatureSelector } from '@ngrx/store';
import { UserState } from './users.reducer';

export const selectUserState = createFeatureSelector<UserState>('user');

export const selectUser = createSelector(selectUserState, (state) => state.user);
export const selectUserError = createSelector(selectUserState, (state) => state.error);
export const selectUserLoading = createSelector(selectUserState, (state) => state.loading);

