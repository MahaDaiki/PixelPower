import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { ProfileComponent } from './profile/profile.component';
import {UsersService} from '../../service/users.service';
import {userReducer} from '../../store/users/users.reducer';
import {StoreModule} from '@ngrx/store';
import {ReactiveFormsModule} from '@angular/forms';
import {GamesModule} from "../games/games.module";
import {ConfigurationsModule} from "../configurations/configurations.module";
import { AdminComponent } from './admin/admin.component';


@NgModule({
  declarations: [
    ProfileComponent,
    AdminComponent
  ],
  imports: [
    CommonModule,
    ConfigurationsModule,
    UsersRoutingModule,
    ReactiveFormsModule,
    StoreModule.forFeature('user', userReducer),

  ],
  providers: [UsersService],
})
export class UsersModule { }
