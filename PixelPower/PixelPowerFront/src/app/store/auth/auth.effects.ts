import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {AuthService} from '../../service/auth.service';
import {catchError, map, mergeMap, of} from 'rxjs';
import {login, loginFailure, loginSuccess, register, registerFailure, registerSuccess} from './auth.actions';


@Injectable()
export class AuthEffects {
  constructor(
    private actions$: Actions,
    private authService: AuthService
  ) {}

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(login),
      mergeMap(({ email, password }) =>
        this.authService.login(email, password).pipe(
          map((response) => loginSuccess({ token: response.token })),
          catchError((error) => of(loginFailure({ error: error.message })))
        )
      )
    )
  );

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(register),
      mergeMap(({ username, email, password, confirmPassword, profilePicture }) =>
        this.authService.register(username, email, password, profilePicture).pipe(
          map((response) => registerSuccess({ message: response.message })),
          catchError((error) => of(registerFailure({ error: error.message })))
        )
      )
    )
  );


}
