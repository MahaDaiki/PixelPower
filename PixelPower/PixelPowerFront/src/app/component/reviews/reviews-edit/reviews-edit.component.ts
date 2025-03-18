import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReviewsService} from '../../../service/reviews.service';

@Component({
  selector: 'app-review-edit',
  standalone: false,
  templateUrl: './reviews-edit.component.html',
  styleUrl: './reviews-edit.component.css'
})
export class ReviewsEditComponent {
  @Input() reviewId: number = 0;
  @Input() isVisible: boolean = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() reviewUpdated = new EventEmitter<void>();

  editForm: FormGroup;
  isLoading: boolean = false;
  review: any = null;


  lastClickedStar: number | null = null
  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewsService
  ) {
    this.editForm = this.fb.group({
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', [Validators.required, Validators.maxLength(500)]]
    });
  }

  ngOnInit(): void {

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isVisible'] && changes['isVisible'].currentValue === true) {
      this.loadReviewData();
    }

    if (changes['reviewId'] && this.isVisible) {
      this.loadReviewData();
    }
  }

  selectRating(rating: number): void {
    this.lastClickedStar = rating
    this.editForm.patchValue({ rating })

    // Reset the clicked state after animation completes
    setTimeout(() => {
      this.lastClickedStar = null
    }, 300)
  }

  loadReviewData(): void {
    if (!this.reviewId) return;

    this.isLoading = true;
    this.reviewService.getReviewById(this.reviewId).subscribe(
      (review) => {
        this.review = review;
        this.editForm.patchValue({
          rating: review.rating,
          comment: review.comment
        });
        this.isLoading = false;
      },
      (error) => {
        console.error('Error loading review:', error);
        this.isLoading = false;
      }
    );
  }

  onSubmit(): void {
    if (this.editForm.valid) {
      this.isLoading = true;
      const updatedReview = {
        ...this.review,
        rating: this.editForm.value.rating,
        comment: this.editForm.value.comment
      };

      this.reviewService.updateReview(this.reviewId, updatedReview).subscribe(
        () => {
          this.isLoading = false;
          this.reviewUpdated.emit();
          this.closeModal.emit();
        },
        (error) => {
          console.error('Error updating review:', error);
          this.isLoading = false;
        }
      );
    }
  }

  onCancel(): void {
    this.closeModal.emit();
  }
}
