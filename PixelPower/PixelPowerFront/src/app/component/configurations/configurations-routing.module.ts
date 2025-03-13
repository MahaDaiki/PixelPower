import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddConfigurationsComponent} from './add-configurations/add-configurations.component';

const routes: Routes = [
  { path: 'add', component: AddConfigurationsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigurationsRoutingModule { }
