import { Component } from '@angular/core';
import {Store} from '@ngrx/store';
import { loadToken } from './store/auth/auth.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent   {
  title = 'PixelPowerFront';

  constructor(private store: Store) {}

  ngOnInit(): void {
    const token = localStorage.getItem('auth_token');

    if (token) {
      this.store.dispatch(loadToken({ token }));
    }
  }
}
