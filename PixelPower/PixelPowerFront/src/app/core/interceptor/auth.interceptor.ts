import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest
} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {catchError, map, Observable, switchMap, take, throwError} from 'rxjs';
import {Store} from '@ngrx/store';
import {selectAuthToken} from '../../store/auth/auth.selectors';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store,  private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.store.select(selectAuthToken).pipe(
      take(1),
      switchMap((token) => {
        if (!token) {
          token = localStorage.getItem('auth_token');
        }

        let request = req;
        if (token) {
          request = req.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`
            }
          });
        }

        return next.handle(request).pipe(
          catchError(error => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
              localStorage.removeItem('auth_token');
              this.router.navigate(['/login']);
            }
            return throwError(() => error);
          })
        );
      })
    );
  }
}
