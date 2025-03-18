import {Component, Input, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReviewsService} from '../../../service/reviews.service';
import {AuthService} from '../../../service/auth.service';

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
  currentUser: any;

  editModalVisible: boolean = false;
  deleteModalVisible: boolean = false;
  selectedReviewId: number = 0;


  constructor(private fb: FormBuilder, private reviewService: ReviewsService,private authService: AuthService) {
    this.reviewForm = this.fb.group({
      gameName: [this.gameName, Validators.required],
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', Validators.maxLength(500)]
    });
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getUser();
    // console.log(this.currentUser.sub)
    if (this.gameName) {
      this.reviewForm.patchValue({
        gameName: this.gameName
      });
    }
  }

  canEditOrDelete(review: any): boolean {
    return this.currentUser && review.user?.email === this.currentUser.sub;

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
          // console.log('Review created:', response);
          this.reviewForm.reset();
          this.loadReviews();
        },
        (error) => {
          alert("something is wrong")
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

  openEditModal(reviewId: number): void {
    this.selectedReviewId = reviewId;
    this.editModalVisible = true;
  }

  closeEditModal(): void {
    this.editModalVisible = false;
  }


  openDeleteModal(reviewId: number): void {
    this.selectedReviewId = reviewId;
    this.deleteModalVisible = true;
  }

  closeDeleteModal(): void {
    this.deleteModalVisible = false;
  }

  onReviewUpdated(): void {
    this.loadReviews();
  }

  onReviewDeleted(): void {
    this.loadReviews();
  }
}
