import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomePageComponent} from './component/home-page/home-page.component';
import {NoAuthGuard} from './core/guards/no-auth.guard';
import {AuthGuard} from './core/guards/auth.guard';
import {NotAuthorizedComponent} from './component/shared/not-authorized/not-authorized.component';
import {NotFoundComponent} from './component/shared/not-found/not-found.component';

const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./component/auth/auth.module').then(m => m.AuthModule), canActivate: [NoAuthGuard] },
  { path: 'games', loadChildren: () => import('./component/games/games.module').then(m => m.GamesModule) },
  { path: 'profile', loadChildren: () => import('./component/users/users.module').then(m => m.UsersModule), canActivate: [AuthGuard] },
  { path: 'config', loadChildren: () => import('./component/configurations/configurations.module').then(m => m.ConfigurationsModule), canActivate: [AuthGuard] },
  { path: '', component: HomePageComponent },
  { path: 'not-authorized', component: NotAuthorizedComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
