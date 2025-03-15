import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GameListComponent} from './game-list/game-list.component';
import {GameDetailsComponent} from './game-details/game-details.component';
import {GamecomparaisonComponent} from './gamecomparaison/gamecomparaison.component';

const routes: Routes = [
  { path: 'all', component: GameListComponent },
  { path: ':id', component: GameDetailsComponent },
  { path: 'compare/:appId', component: GamecomparaisonComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GamesRoutingModule { }
