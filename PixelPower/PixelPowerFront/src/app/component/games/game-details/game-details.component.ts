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

  selectedPlatform: "pc" | "mac" | "linux" = "pc"
  selectedTab: "minimum" | "recommended" = "minimum"

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

  // Add this method to handle platform selection
  selectPlatform(platform: "pc" | "mac" | "linux"): void {
    this.selectedPlatform = platform
    // Reset to minimum tab when switching platforms
    this.selectedTab = "minimum"
  }

  // Update the showTab method to work with platform-specific requirements
  showTab(tab: "minimum" | "recommended"): void {
    this.selectedTab = tab
  }

  // Add a helper method to get the current platform requirements
  getPlatformRequirements(game: any): any {
    if (!game) return null

    switch (this.selectedPlatform) {
      case "pc":
        return game.pc_requirements
      case "mac":
        return game.mac_requirements
      case "linux":
        return game.linux_requirements
      default:
        return game.pc_requirements
    }
  }

  // Add a helper method to check if a platform has requirements
  hasPlatformRequirements(game: any, platform: "pc" | "mac" | "linux"): boolean {
    if (!game) return false

    const requirements =
      platform === "pc" ? game.pc_requirements : platform === "mac" ? game.mac_requirements : game.linux_requirements

    return requirements && (requirements.minimum || requirements.recommended)
  }


}
