import {createAction, props} from '@ngrx/store';


export const loadUser = createAction('[User] loadUser');
export const loadUserSuccess = createAction('[User] Load User Success', props<{ user: any }>());
export const loadUserFailure = createAction('[User] Load User Failure', props<{ error: any }>());

export const updateUser = createAction('[User] Update User', props<{ userData: { email: string; username: string } }>());
export const updateUserSuccess = createAction('[User] Update User Success', props<{ user: any }>());
export const updateUserFailure = createAction('[User] Update User Failure', props<{ error: any }>());


export const updateAvatar = createAction('[User] Update Avatar', props<{ avatarPath: string }>());
export const updateAvatarSuccess = createAction('[User] Update Avatar Success', props<{ user: any }>());
export const updateAvatarFailure = createAction('[User] Update Avatar Failure', props<{ error: any }>());


export const updatePassword = createAction(
  '[User] Update Password',
  props<{ newPassword: string }>()
);

export const updatePasswordSuccess = createAction(
  '[User] Update Password Success',
  props<{ user: any }>()
);

export const updatePasswordFailure = createAction(
  '[User] Update Password Failure',
  props<{ error: any }>()
);

