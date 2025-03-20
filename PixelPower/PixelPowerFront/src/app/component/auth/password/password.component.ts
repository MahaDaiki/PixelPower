import { Component } from '@angular/core';
import {PasswordService} from '../../../service/password.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {catchError, finalize, of} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-password',
  standalone: false,
  templateUrl: './password.component.html',
  styleUrl: './password.component.css'
})
export class PasswordComponent {
  emailForm: FormGroup;
  tokenForm: FormGroup;
  emailSent = false;
  email = '';
  timeRemaining = 120;
  timer: any;
  message: string = '';
  canResend = false;

  constructor(
    private fb: FormBuilder,
    private passwordResetService: PasswordService,
    private router: Router
  ) {
    this.emailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });

    this.tokenForm = this.fb.group({
      token: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {validators: this.passwordMatchValidator});
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.stopTimer();
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      form.setErrors({passwordMismatch: true});
      return {passwordMismatch: true};
    }

    return null;
  }

  onEmailSubmit() {
    if (this.emailForm.valid) {
      this.message = 'Sending reset email...';
      this.email = this.emailForm.value.email;

      this.passwordResetService.sendResetEmail(this.email)
        .pipe(
          catchError(err => {
            console.error('Error sending email:', err);
            this.message = 'Error sending reset email! Please try again.';
            return of(null);
          }),
          finalize(() => {
            this.emailSent = true;
            this.startTimer();
            if (!this.message || this.message === 'Sending reset email...') {
              this.message = 'Password reset link sent! Please check your email.';
            }
          })
        )
        .subscribe(response => {
          if (response) {
            console.log('Response from backend:', response);
          }
        });
    }
  }


  onTokenSubmit() {
    if (this.tokenForm.valid) {
      const { token, newPassword } = this.tokenForm.value;
      this.message = 'Resetting password...';

      this.passwordResetService.resetPassword(token, newPassword)
        .pipe(
          catchError(err => {
            console.error('Error resetting password:', err);
            this.message = 'Error resetting password! Token may be invalid or expired.';
            return of(null);
          })
        )
        .subscribe({
          next: (response) => {
            if (response) {
              console.log('Response from backend:', response);
              this.message = 'Password reset successfully!';
              this.stopTimer();
              this.router.navigate(['/auth/login']);
            }
          }
        });
    }
  }


  startTimer() {
    this.timeRemaining = 120;
    this.canResend = false;

    this.timer = setInterval(() => {
      if (this.timeRemaining > 0) {
        this.timeRemaining--;
      } else {
        this.canResend = true;
        this.stopTimer();
      }
    }, 1000);
  }

  stopTimer() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }

  resendToken() {
    if (this.email && !this.canResend) {
      this.message = 'Resending reset email...';
      this.passwordResetService.sendResetEmail(this.email)
        .pipe(
          catchError(err => {
            console.error('Error resending email:', err);
            this.message = 'Error resending reset email! Please try again.';
            return of(null);
          }),
          finalize(() => {
            this.startTimer();
            this.message = 'Password reset link sent again! Please check your email.';
          })
        )
        .subscribe();
    }
  }
}
