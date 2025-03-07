import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {authReducer} from '../../store/auth/auth.reducer';
import {StoreModule} from '@ngrx/store';
import {AuthService} from '../../service/auth.service';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forFeature('auth', authReducer),
  ],
  providers: [AuthService],
})
export class AuthModule { }
