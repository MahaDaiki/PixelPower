import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GamesRoutingModule } from './games-routing.module';
import { GameListComponent } from './game-list/game-list.component';
import {StoreModule} from '@ngrx/store';
import {gameReducer} from '../../store/games/games.reducer';
import {GamesService} from '../../service/games.service';


@NgModule({
  declarations: [
    GameListComponent
  ],
  imports: [
    CommonModule,
    GamesRoutingModule,
    StoreModule.forFeature('game', gameReducer),
  ],
  providers: [GamesService],
})
export class GamesModule { }
