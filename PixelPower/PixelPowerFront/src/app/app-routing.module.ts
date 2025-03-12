import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomePageComponent} from './component/home-page/home-page.component';

const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./component/auth/auth.module').then(m => m.AuthModule) },
  { path: 'games', loadChildren: () => import('./component/games/games.module').then(m => m.GamesModule) },
  { path: 'profile', loadChildren: () => import('./component/users/users.module').then(m => m.UsersModule) },
  { path: '', component: HomePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
