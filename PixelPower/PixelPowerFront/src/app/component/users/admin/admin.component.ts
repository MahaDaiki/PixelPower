import { Component } from '@angular/core';
import {UsersService} from '../../../service/users.service';
import {ReviewsService} from '../../../service/reviews.service';

@Component({
  selector: 'app-admin',
  standalone: false,
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  users: any[] = [];
  reviews: any[] = [];

  showDeleteModal = false
  deleteType: "user" | "review" | null = null
  itemToDelete: any = null
  deleteLoading = false
  deleteError: string | null = null

  constructor(private usersService: UsersService, private reviewsService: ReviewsService) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadReviews();
  }

  loadUsers(): void {
    this.usersService.getUsers().subscribe({
      next: (data) => {
        this.users = data
      },
      error: (error) => {
        console.error("Error loading users:", error)
      },
    })
  }

  loadReviews(): void {
    this.reviewsService.getReviews().subscribe({
      next: (data) => {
        this.reviews = data
      },
      error: (error) => {
        console.error("Error loading reviews:", error)
      },
    })
  }

  // Delete modal methods
  openDeleteModal(type: "user" | "review", item: any): void {
    this.deleteType = type
    this.itemToDelete = item
    this.deleteError = null
    this.showDeleteModal = true
  }

  closeDeleteModal(event: MouseEvent): void {
    if (
      event.target === event.currentTarget ||
      (event.target as HTMLElement).classList.contains("close-modal") ||
      (event.target as HTMLElement).classList.contains("cancel-btn")
    ) {
      this.showDeleteModal = false
      this.deleteType = null
      this.itemToDelete = null
      this.deleteLoading = false
    }
  }

  confirmDelete(): void {
    if (!this.itemToDelete || !this.deleteType) {
      return
    }

    this.deleteLoading = true
    this.deleteError = null

    if (this.deleteType === "user") {
      this.deleteUser(this.itemToDelete.id)
    } else if (this.deleteType === "review") {
      this.deleteReview(this.itemToDelete.id)
    }
  }

  deleteUser(userId: number): void {
    this.usersService.deleteUser(userId).subscribe({
      next: () => {
        this.users = this.users.filter((user) => user.id !== userId)
        this.deleteLoading = false
        this.showDeleteModal = false
        this.itemToDelete = null
        this.deleteType = null
      },
      error: (error) => {
        console.error("Error deleting user:", error)
        this.deleteError = error.message || "Failed to delete user. Please try again."
        this.deleteLoading = false
      },
    })
  }

  deleteReview(reviewId: number): void {
    this.reviewsService.deleteReview(reviewId).subscribe({
      next: () => {
        this.reviews = this.reviews.filter((review) => review.id !== reviewId)
        this.deleteLoading = false
        this.showDeleteModal = false
        this.itemToDelete = null
        this.deleteType = null
      },
      error: (error) => {
        console.error("Error deleting review:", error)
        this.deleteError = error.message || "Failed to delete review. Please try again."
        this.deleteLoading = false
      },
    })
  }
}
