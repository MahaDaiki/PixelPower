import { createReducer, on } from '@ngrx/store';
import * as UserActions from './users.actions';

export interface UserState {
  user: any | null;
  error: any | null;
  loading: boolean;
}

const initialState: UserState = {
  user: null,
  error: null,
  loading: false
};

export const userReducer = createReducer(
  initialState,
  on(UserActions.loadUser, state => ({ ...state, loading: true })),
  on(UserActions.loadUserSuccess, (state, { user }) => ({ ...state, user, error: null, loading: false })),
  on(UserActions.loadUserFailure, (state, { error }) => ({ ...state, error, loading: false })),

  on(UserActions.updateUser, state => ({ ...state, loading: true })),
  on(UserActions.updateUserSuccess, (state, { user }) => ({ ...state, user, error: null, loading: false })),
  on(UserActions.updateUserFailure, (state, { error }) => ({ ...state, error, loading: false })),

  on(UserActions.updateAvatar, state => ({ ...state, loading: true })),
  on(UserActions.updateAvatarSuccess, (state, { user }) => ({ ...state, user, error: null, loading: false })),
  on(UserActions.updateAvatarFailure, (state, { error }) => ({ ...state, error, loading: false })),

on(UserActions.updatePassword, (state) => ({
  ...state,
  loading: true,
})),
  on(UserActions.updatePasswordSuccess, (state, { user }) => ({
    ...state,
    user,
    error: null,
    loading: false,
  })),
  on(UserActions.updatePasswordFailure, (state, { error }) => ({
    ...state,
    error,
    loading: false,
  }))
);
