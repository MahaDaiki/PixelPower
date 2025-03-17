import {Component, Input, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReviewsService} from '../../../service/reviews.service';

@Component({
  selector: 'app-reviews-display',
  standalone: false,
  templateUrl: './reviews-display.component.html',
  styleUrl: './reviews-display.component.css'
})
export class ReviewsDisplayComponent {
  @Input() gameName: string = '';

  reviewForm: FormGroup;
  reviews: any[] = [];

  constructor(private fb: FormBuilder, private reviewService: ReviewsService) {
    this.reviewForm = this.fb.group({
      gameName: [this.gameName, Validators.required],
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', Validators.maxLength(500)]
    });
  }

  ngOnInit(): void {
    if (this.gameName) {
      this.reviewForm.patchValue({
        gameName: this.gameName
      });
    }

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['gameName'] && changes['gameName'].currentValue) {
      this.loadReviews();
    }
  }


  onSubmit(): void {
    if (this.reviewForm.valid) {
      const review = this.reviewForm.value;
      this.reviewService.createReview(review).subscribe(
        (response) => {
          console.log('Review created:', response);
          this.loadReviews();
        },
        (error) => {
          console.error('Error creating review:', error);
        }
      );
    }
  }


  loadReviews(): void {
    if (this.gameName) {
      this.reviewService.getReviewsByGameName(this.gameName).subscribe(
        (reviews) => {
          this.reviews = reviews;
        },
        (error) => {
          console.error('Error fetching reviews:', error);
        }
      );
    }
  }
}
