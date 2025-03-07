import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {Store} from '@ngrx/store';
import {selectAuthError, selectAuthMessage} from '../../../store/auth/auth.selectors';
import {register} from '../../../store/auth/auth.actions';
import {AuthService} from '../../../service/auth.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm: FormGroup;
  message$: Observable<string | null>;
  error$: Observable<string | null>;
  selectedFile: File| null = null;

  constructor(private fRegisterb: FormBuilder, private store: Store, private authService: AuthService) {
    this.registerForm = this.fRegisterb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      profilePicture: [null]
    }, { validator: this.passwordMatchValidator });

    this.message$ = this.store.select(selectAuthMessage);
    this.error$ = this.store.select(selectAuthError);
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0] || null;
    this.selectedFile = file;
    this.registerForm.patchValue({ profilePicture: file });
  }

  registerUser() {
    if (this.registerForm.valid) {
      const { username, email, password } = this.registerForm.value;
      const profilePicture = this.selectedFile;


      console.log('Form Values:', { username, email, password });
      console.log('Selected File:', profilePicture);


      this.authService.register(username, email, password, profilePicture).subscribe(
        (response) => {
          console.log('Registration successful:', response);
        },
        (error) => {
          console.error('Registration failed:', error);
        }
      );
    } else {
      console.error('Form is not valid');
    }

}
}
