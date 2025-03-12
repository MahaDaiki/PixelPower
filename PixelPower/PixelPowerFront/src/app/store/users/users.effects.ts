import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { UsersService } from '../../service/users.service';
import * as UserActions from './users.actions';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class UserEffects {
  constructor(private actions$: Actions, private userService: UsersService) {}

  loadUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.loadUser),
      mergeMap(() =>
        this.userService.getProfile().pipe(
          map(user => UserActions.loadUserSuccess({ user })),
          catchError(error => of(UserActions.loadUserFailure({ error })))
        )
      )
    )
  );


  updateUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.updateUser),
      mergeMap(action =>
        this.userService.updateProfile(action.userData).pipe(
          map(user => UserActions.updateUserSuccess({ user })),
          catchError(error => of(UserActions.updateUserFailure({ error })))
        )
      )
    )
  );

  updateAvatar$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.updateAvatar),
      mergeMap(action =>
        this.userService.updateAvatar(action.avatarPath).pipe(
          map(user => UserActions.updateAvatarSuccess({ user })),
          catchError(error => of(UserActions.updateAvatarFailure({ error })))
        )
      )
    )
  );




}
