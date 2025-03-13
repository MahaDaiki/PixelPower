import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConfigurationsRoutingModule } from './configurations-routing.module';
import { DisplayConfigurationsComponent } from './display-configurations/display-configurations.component';
import {ConfigurationsService} from '../../service/configurations.service';
import { AddConfigurationsComponent } from './add-configurations/add-configurations.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    DisplayConfigurationsComponent,
    AddConfigurationsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ConfigurationsRoutingModule
  ],
  providers: [ConfigurationsService],
  exports: [DisplayConfigurationsComponent],
})
export class ConfigurationsModule { }
