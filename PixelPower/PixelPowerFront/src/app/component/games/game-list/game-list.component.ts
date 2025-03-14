import {Component, OnInit} from '@angular/core';
import {GamesService} from '../../../service/games.service';
import {Observable, of, Subject, switchMap, tap} from 'rxjs';
import {
  selectGameError,
  selectGameLoading,
  selectGames,
  selectIsSearching,
  selectSearchResults
} from '../../../store/games/games.selectors';
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-game-list',
  standalone: false,
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent implements OnInit{
  games$: Observable<any[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  searchResults$: Observable<any[]>;
  isSearching$: Observable<boolean>;

  searchTerm: string = '';
  private searchTerms = new Subject<string>();

  page = 2;
  size = 10;

  constructor(private store: Store, private gamesService: GamesService) {
    this.games$ = this.store.select(selectGames);
    this.searchResults$ = this.store.select(selectSearchResults);
    this.isSearching$ = this.store.select(selectIsSearching);
    this.loading$ = this.store.select(selectGameLoading);
    this.error$ = this.store.select(selectGameError);
  }

  ngOnInit(): void {
    console.log('GameListComponent loaded!');
    this.loadGames();

    this.searchTerms.subscribe(term => {
      if (term.trim()) {
        this.searchGames(term);
      } else {
        this.clearSearch();
      }
    });
  }




  loadGames(): void {
    console.log('Loading games...');
    this.gamesService.getGames(this.page, this.size).subscribe({
      error: (error) => console.error('Error loading games in component:', error)
    });
  }

  searchGames(term: string): void {
    this.searchTerm = term;
    if (term.trim()) {
      this.isSearching$ = of(true);
      this.searchResults$ = this.gamesService.searchGames(term).pipe(
        tap(games => {
          console.log('Search results updated:', games);
          this.isSearching$ = of(false);
        })
      );
    } else {
      this.clearSearch();
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.isSearching$ = of(false);
    this.searchResults$ = of([]);
    this.loadGames();
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
