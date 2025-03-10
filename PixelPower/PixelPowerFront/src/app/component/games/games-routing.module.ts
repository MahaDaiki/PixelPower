import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GameListComponent} from './game-list/game-list.component';
import {GameDetailsComponent} from './game-details/game-details.component';

const routes: Routes = [
  { path: 'all', component: GameListComponent },
  { path: ':id', component: GameDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GamesRoutingModule { }
