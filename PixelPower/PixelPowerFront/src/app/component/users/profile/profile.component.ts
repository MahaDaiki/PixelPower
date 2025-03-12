import {Component, ElementRef, ViewChild} from '@angular/core';
import {UsersService} from '../../../service/users.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  @ViewChild("fileInput") fileInput!: ElementRef

  user: any
  error: any
  loading = false

  // Error messages
  errorMessage: string | null = null
  avatarErrorMessage: string | null = null

  // Success messages
  successMessage: string | null = null
  avatarSuccessMessage: string | null = null
  passwordSuccessMessage: string | null = null

  // Modal controls
  showProfileModal = false
  showAvatarModal = false
  showPasswordForm = false

  // Forms
  profileForm!: FormGroup
  passwordForm!: FormGroup

  // Avatar selection
  availableAvatars = [
    "profile_pictures/test4.jpg",
    // "profile_pictures/avatar2.png",
    // "profile_pictures/avatar3.png",
    // "profile_pictures/avatar4.png",
    // "profile_pictures/avatar5.png",
    // "profile_pictures/avatar6.png",
    // "profile_pictures/avatar7.png",
    // "profile_pictures/avatar8.png",
    "profile_pictures/pict.gif",
    // "profile_pictures/default.png",
  ]
  selectedAvatar: string | null = null
  uploadedFile: File | null = null

  constructor(
    private usersService: UsersService,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    // Initialize forms
    this.profileForm = this.fb.group({
      username: ["", Validators.required],
      email: ["", [Validators.required, Validators.email]],
    })

    this.passwordForm = this.fb.group(
      {
        newPassword: ["", [Validators.required, Validators.minLength(6)]],
        confirmNewPassword: ["", Validators.required],
      },
      { validator: this.passwordsMatch },
    )

    // Load user profile
    this.loading = true
    this.usersService.getProfile().subscribe({
      next: (data) => {
        this.user = data
        console.log("User data loaded:", this.user)

        if (this.user) {
          console.log("Setting form values:", {
            username: this.user.username,
            email: this.user.email,
          })

          this.profileForm.patchValue({
            username: this.user.username || "",
            email: this.user.email || "",
          })
        }
        this.loading = false
      },
      error: (err) => {
        console.error("Error loading user data:", err)
        this.error = err
        this.loading = false
      },
    })
  }

  openPasswordForm(): void {
    this.showPasswordForm = true
    this.errorMessage = null
    this.passwordSuccessMessage = null
    this.passwordForm.reset()
  }

  openProfileModal(): void {
    if (this.user) {
      console.log("Opening modal with user data:", this.user)
      this.profileForm.patchValue({
        username: this.user.username || "",
        email: this.user.email || "",
      })

      this.errorMessage = null
      this.successMessage = null
      this.showPasswordForm = false
      this.showProfileModal = true
    }
  }

  closeProfileModal(event: MouseEvent): void {
    if (
      event.target === event.currentTarget ||
      (event.target as HTMLElement).classList.contains("close-modal") ||
      (event.target as HTMLElement).classList.contains("cancel-btn")
    ) {
      this.showProfileModal = false
      this.showPasswordForm = false
    }
  }

  updateProfile(event: Event): void {
    event.preventDefault()

    if (this.profileForm.invalid) {
      this.errorMessage = "Please fill in all required fields correctly."
      return
    }

    this.loading = true
    this.errorMessage = null
    this.successMessage = null

    this.usersService.updateProfile(this.profileForm.value).subscribe({
      next: (updatedUser) => {
        console.log("Profile updated successfully:", updatedUser)
        this.user = { ...this.user, ...updatedUser }
        this.successMessage = "Profile updated successfully!"
        this.loading = false

        // Close modal after a short delay to show success message
        setTimeout(() => {
          this.showProfileModal = false
        }, 1500)
      },
      error: (err) => {
        console.error("Error updating profile:", err)
        this.errorMessage = "Failed to update profile. Please try again."
        this.loading = false
      },
    })
  }

  updatePassword(event: Event): void {
    event.preventDefault()
    if (this.passwordForm.invalid) {
      this.errorMessage = "All fields are required."
      return
    }

    const { newPassword, confirmNewPassword } = this.passwordForm.value

    if (newPassword !== confirmNewPassword) {
      this.errorMessage = "New passwords do not match!"
      return
    }

    this.loading = true
    this.errorMessage = null
    this.passwordSuccessMessage = null

    this.usersService.updatePassword(newPassword).subscribe({
      next: () => {
        console.log("Password updated successfully")
        this.passwordSuccessMessage = "Password changed successfully!"
        this.loading = false

        setTimeout(() => {
          this.showPasswordForm = false
        }, 1500)
      },
      error: (err) => {
        console.error("Error updating password:", err)
        this.errorMessage = "Failed to update password. Please try again."
        this.loading = false
      },
    })
  }

  passwordsMatch(group: FormGroup): { [key: string]: boolean } | null {
    return group.get("newPassword")?.value === group.get("confirmNewPassword")?.value ? null : { mismatch: true }
  }

  openAvatarModal(): void {
    this.selectedAvatar = this.user?.profilePicture || null
    this.uploadedFile = null
    this.avatarErrorMessage = null
    this.avatarSuccessMessage = null
    this.showAvatarModal = true
  }

  closeAvatarModal(event: MouseEvent): void {
    if (
      event.target === event.currentTarget ||
      (event.target as HTMLElement).classList.contains("close-modal") ||
      (event.target as HTMLElement).classList.contains("cancel-btn")
    ) {
      this.showAvatarModal = false
    }
  }

  selectAvatar(avatar: string): void {
    this.selectedAvatar = avatar
    this.uploadedFile = null
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement
    if (input.files && input.files.length > 0) {
      const file = input.files[0]

      if (!file.type.match(/image\/(jpeg|jpg|png|gif)$/)) {
        this.avatarErrorMessage = "Please select a valid image file (JPEG, PNG, or GIF)."
        return
      }

      if (file.size > 2 * 1024 * 1024) {
        this.avatarErrorMessage = "Image size should not exceed 2MB."
        return
      }

      this.uploadedFile = file
      this.selectedAvatar = null
      this.avatarErrorMessage = null
    }
  }

  updateAvatar(): void {
    if (!this.selectedAvatar && !this.uploadedFile) {
      this.avatarErrorMessage = "Please select an avatar or upload an image."
      return
    }

    this.loading = true
    this.avatarErrorMessage = null
    this.avatarSuccessMessage = null

    const formData = new FormData()

    if (this.uploadedFile) {
      formData.append("profilepicture", this.uploadedFile)
      this.sendAvatarUpdate(formData)
    } else if (this.selectedAvatar) {
      this.convertUrlToFile(this.selectedAvatar)
        .then((file) => {
          formData.append("profilepicture", file)
          this.sendAvatarUpdate(formData)
        })
        .catch((error) => {
          console.error("Error converting URL to file:", error)
          this.avatarErrorMessage = "Error processing the selected avatar."
          this.loading = false
        })
    }
  }

  sendAvatarUpdate(formData: FormData): void {
    this.usersService.updateAvatar(formData).subscribe({
      next: (response) => {
        console.log("Avatar updated successfully:", response)
        if (this.user) {
          this.user.profilePicture = response.profilePicture
        }
        this.avatarSuccessMessage = "Avatar updated successfully!"
        this.loading = false
        setTimeout(() => {
          this.showAvatarModal = false
        }, 1500)
      },
      error: (err) => {
        console.error("Error updating avatar:", err)
        this.avatarErrorMessage = "Failed to update avatar. Please try again."
        this.loading = false
      },
    })
  }

  convertUrlToFile(imageUrl: string): Promise<File> {
    return fetch(imageUrl)
      .then((response) => response.blob())
      .then((blob) => new File([blob], "avatar.png", { type: blob.type }))
  }
}

