import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GamesRoutingModule } from './games-routing.module';
import { GameListComponent } from './game-list/game-list.component';
import {StoreModule} from '@ngrx/store';
import {gameReducer} from '../../store/games/games.reducer';
import {GamesService} from '../../service/games.service';
import { GameDetailsComponent } from './game-details/game-details.component';
import {FormsModule} from '@angular/forms';
import { GamecomparaisonComponent } from './gamecomparaison/gamecomparaison.component';
import {GamecomparaisonService} from '../../service/gamecomparaison.service';


@NgModule({
  declarations: [
    GameListComponent,
    GameDetailsComponent,
    GamecomparaisonComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    GamesRoutingModule,
    StoreModule.forFeature('game', gameReducer),
  ],
  providers: [GamesService, GamecomparaisonService],
})
export class GamesModule { }
