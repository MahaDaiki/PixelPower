import {Component, Input} from '@angular/core';
import {UpgradesuggestionService} from '../../../service/upgradesuggestion.service';

@Component({
  selector: 'app-upgradesuggestion',
  standalone: false,
  templateUrl: './upgradesuggestion.component.html',
  styleUrl: './upgradesuggestion.component.css'
})
export class UpgradesuggestionComponent {
  @Input() comparisonId!: number;
  upgradeSuggestions: any[] = [];
  loading = false;
  errorMessage: string | null = null;

  constructor(private upgradeSuggestionService: UpgradesuggestionService) {}

  ngOnInit(): void {

      this.generateSuggestions();

    console.log("alo")

  }

  generateSuggestions(): void {
    this.loading = true;
    this.errorMessage = null;

    this.upgradeSuggestionService.generateSuggestions(this.comparisonId).subscribe({
      next: (suggestions) => {
        this.upgradeSuggestions = suggestions;
        console.log('Upgrade suggestions generated:', suggestions);
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to generate upgrade suggestions';
        console.error('Error generating suggestions:', error);
        this.loading = false;
      }
    });
  }
}
