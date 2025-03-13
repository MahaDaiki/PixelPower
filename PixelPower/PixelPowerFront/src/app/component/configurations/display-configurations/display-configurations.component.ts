import { Component } from '@angular/core';
import {ConfigurationsService} from '../../../service/configurations.service';

@Component({
  selector: 'app-display-configurations',
  standalone: false,
  templateUrl: './display-configurations.component.html',
  styleUrl: './display-configurations.component.css'
})
export class DisplayConfigurationsComponent {
  configurations: any[] = [];

  constructor(private configurationService: ConfigurationsService) {}

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



}
