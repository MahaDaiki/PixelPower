import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GamecomparaisonService} from '../../../service/gamecomparaison.service';

@Component({
  selector: 'app-gamecomparaison',
  standalone: false,
  templateUrl: './gamecomparaison.component.html',
  styleUrl: './gamecomparaison.component.css'
})
export class GamecomparaisonComponent {

  gameData: any;
  isLoading = true;
  appId!: number;

  constructor(
    private route: ActivatedRoute,
    private gameComparisonService: GamecomparaisonService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.appId = Number(params.get('appId'));
      this.fetchComparison();
    });
  }

  fetchComparison(): void {
    this.isLoading = true;
    this.gameComparisonService.compareGame(this.appId).subscribe({
      next: (data) => {
        this.gameData = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching data', err);
        this.isLoading = false;
      }
    });
  }


}
