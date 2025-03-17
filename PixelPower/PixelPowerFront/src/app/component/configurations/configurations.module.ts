import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConfigurationsRoutingModule } from './configurations-routing.module';
import { DisplayConfigurationsComponent } from './display-configurations/display-configurations.component';
import {ConfigurationsService} from '../../service/configurations.service';
import { AddConfigurationsComponent } from './add-configurations/add-configurations.component';
import {ReactiveFormsModule} from '@angular/forms';
import { EditModalComponent } from './edit-modal/edit-modal.component';
import { DeleteModalComponent } from './delete-modal/delete-modal.component';
import {UpgradesuggestionService} from '../../service/upgradesuggestion.service';


@NgModule({
  declarations: [
    DisplayConfigurationsComponent,
    AddConfigurationsComponent,
    EditModalComponent,
    DeleteModalComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ConfigurationsRoutingModule
  ],
  providers: [ConfigurationsService, UpgradesuggestionService],
  exports: [DisplayConfigurationsComponent],
})
export class ConfigurationsModule { }
