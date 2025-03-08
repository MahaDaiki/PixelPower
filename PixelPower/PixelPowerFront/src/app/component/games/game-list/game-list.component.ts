import { Component } from '@angular/core';
import {GamesService} from '../../../service/games.service';
import {catchError, Observable, of, tap} from 'rxjs';

@Component({
  selector: 'app-game-list',
  standalone: false,
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent {
  games$: Observable<any[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  page = 2;
  size = 10;

  constructor(private gamesService: GamesService) {
    this.games$ = this.gamesService.getGamesSelector();
    this.loading$ = this.gamesService.getLoadingSelector();
    this.error$ = this.gamesService.getErrorSelector();
  }

  ngOnInit(): void {
    console.log('GameListComponent loaded!');
    this.loadGames();
  }

  loadGames(): void {
    console.log('Loading games...');
    this.gamesService.getGames(this.page, this.size);
  }

  nextPage(): void {
    this.page++;
    this.loadGames();
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadGames();
    }
  }

}
