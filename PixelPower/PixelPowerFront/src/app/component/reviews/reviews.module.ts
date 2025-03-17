import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReviewsRoutingModule } from './reviews-routing.module';
import { ReviewsDisplayComponent } from './reviews-display/reviews-display.component';
import {ReviewsService} from '../../service/reviews.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    ReviewsDisplayComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ReviewsRoutingModule
  ],
  providers: [ReviewsService],
  exports: [ReviewsDisplayComponent],
})
export class ReviewsModule { }
