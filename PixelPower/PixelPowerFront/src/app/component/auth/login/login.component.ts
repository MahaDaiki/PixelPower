import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {AuthService} from '../../../service/auth.service';
import {Store} from '@ngrx/store';
import {login} from '../../../store/auth/auth.actions';
import {selectAuthError} from '../../../store/auth/auth.selectors';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm : FormGroup;
  error$: Observable<string | null>;

  constructor(
    private formBuilder: FormBuilder,
    private store: Store,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.error$ = this.store.select(selectAuthError);
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.loginUser(email, password);
    }
  }

  get f() {
    return this.loginForm.controls;
  }

  hasError(controlName: string, errorType: string): boolean {
    const control = this.f[controlName];
    return control && control.touched && control.hasError(errorType);
  }

}
