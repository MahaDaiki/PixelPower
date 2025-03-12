import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map, Observable, switchMap, take} from 'rxjs';
import {Store} from '@ngrx/store';
import {selectAuthToken} from '../../store/auth/auth.selectors';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.store.select(selectAuthToken).pipe(
      take(1),
      switchMap((token) => {
        if (!token) {
          token = localStorage.getItem('auth_token');
        }

        console.log('Auth Token:', token);
        if (token) {
          const clonedRequest = req.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`,
            },
          });
          return next.handle(clonedRequest);
        } else {
          return next.handle(req);
        }
      })
    );
  }
}
