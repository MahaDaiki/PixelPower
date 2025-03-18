import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReviewsRoutingModule } from './reviews-routing.module';
import { ReviewsDisplayComponent } from './reviews-display/reviews-display.component';
import {ReviewsService} from '../../service/reviews.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import { ReviewsEditComponent } from './reviews-edit/reviews-edit.component';
import { ReviewsDeleteComponent } from './reviews-delete/reviews-delete.component';


@NgModule({
  declarations: [
    ReviewsDisplayComponent,
    ReviewsEditComponent,
    ReviewsDeleteComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ReviewsRoutingModule
  ],
  providers: [ReviewsService ,AuthService],
  exports: [ReviewsDisplayComponent],
})
export class ReviewsModule { }
