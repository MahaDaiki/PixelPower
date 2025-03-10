import {Component, Input} from '@angular/core';
import {Observable, of} from 'rxjs';
import {GamesService} from '../../../service/games.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-game-details',
  standalone: false,
  templateUrl: './game-details.component.html',
  styleUrl: './game-details.component.css'
})
export class GameDetailsComponent {
  game$: Observable<any> = of(null);
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  activeScreenshot: string | null = null;
  activeVideo: string | null = null;

  @Input() gameId?: number;

  constructor(private gamesService: GamesService, private route: ActivatedRoute) {
    this.loading$ = this.gamesService.getLoadingSelector();
    this.error$ = this.gamesService.getErrorSelector();
  }

  ngOnInit(): void {

    if (!this.gameId) {
      this.route.paramMap.subscribe((params) => {
        const id = Number(params.get('id'));
        if (id) {
          this.loadGame(id);
        }
      });
    } else {
      this.loadGame(this.gameId);
    }
  }

  loadGame(id: number): void {
    console.log('Loading game details for ID:', id);

    this.gamesService.getGameById(id).subscribe({
      next: (response) => {
        const gameData = response[id]?.data;

        this.game$ = of(gameData);

        console.log('Extracted game data:', gameData);
      },
      error: (error) => console.error('Error loading game details in component:', error)
    });
  }


  openScreenshot(url: string): void {
    this.activeScreenshot = url;
  }

  closeScreenshot(): void {
    this.activeScreenshot = null;
  }

  playVideo(url: string): void {
    this.activeVideo = url;
  }

  closeVideo(): void {
    this.activeVideo = null;
  }


}
