import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ReviewsService} from '../../../service/reviews.service';

@Component({
  selector: 'app-review-delete',
  standalone: false,
  templateUrl: './reviews-delete.component.html',
  styleUrl: './reviews-delete.component.css'
})
export class ReviewsDeleteComponent {
  @Input() reviewId: number = 0;
  @Input() isVisible: boolean = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() reviewDeleted = new EventEmitter<void>();

  isLoading: boolean = false;

  constructor(private reviewService: ReviewsService) {}

  confirmDelete(): void {
    this.isLoading = true;
    this.reviewService.deleteReview(this.reviewId).subscribe(
      () => {
        this.isLoading = false;
        this.reviewDeleted.emit();
        this.closeModal.emit();
      },
      (error) => {
        console.error('Error deleting review:', error);
        this.isLoading = false;
      }
    );
  }

  onCancel(): void {
    this.closeModal.emit();
  }
}
