import { Component } from '@angular/core';
import {ConfigurationsService} from '../../../service/configurations.service';
import {UpgradesuggestionService} from '../../../service/upgradesuggestion.service';

@Component({
  selector: 'app-display-configurations',
  standalone: false,
  templateUrl: './display-configurations.component.html',
  styleUrl: './display-configurations.component.css'
})
export class DisplayConfigurationsComponent {
  showEditModal = false;
  showDeleteModal = false;
  showUpgradeModal = false;
  configToEditId: string | null = null;
  configToDeleteId: string | null = null;
  configurations: any[] = [];
  upgradeSuggestions: any[] = [];

  constructor(private configurationService: ConfigurationsService, private upgradesuggestionService: UpgradesuggestionService) {}

  ngOnInit(): void {
    this.fetchConfigurations();

  }

  fetchConfigurations(): void {
    this.configurationService.getConfigurations().subscribe({
      next: (data) => {
        this.configurations = data;
        console.log(data);
      },
      error: (error) => {
        console.error('Error fetching configurations:', error);
      },
    });
  }

  openEditModal(configId: string) {
    this.configToEditId = configId;
    this.showEditModal = true;
  }

  openDeleteModal(configId: string) {
    this.configToDeleteId = configId;
    this.showDeleteModal = true;
  }

  closeEditModal() {
    this.showEditModal = false;
  }

  closeDeleteModal() {
    this.showDeleteModal = false;
  }
  handleDeleted(): void {
    this.showDeleteModal = false;
    this.configToDeleteId = null;
    this.fetchConfigurations();
  }

  handleUpdated(updatedConfig: any): void {
    this.showEditModal = false;
    this.configToEditId = null;
    this.fetchConfigurations();
  }
  closeUpgradeModal(){
    this.showUpgradeModal = false;
}

  displayUpgradesuggestion(comparisonId: number) {
    this.upgradesuggestionService.getSuggestionsByComparisonId(comparisonId).subscribe(
      (response) => {
        console.log(response)
        this.upgradeSuggestions = response;
        this.showUpgradeModal = true;
        },
      (error) => {
        this.showUpgradeModal=true
        console.error('Error fetching comparison details', error);
      }
    );
  }



}
