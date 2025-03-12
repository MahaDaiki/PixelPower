import { Component } from '@angular/core';
import {UsersService} from '../../../service/users.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: any;
  error: any;
  loading = false;

  errorMessage: string | null = null;

  showProfileModal = false
  showAvatarModal = false

  profileForm!: FormGroup;

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

  uploadedFile: File | null = null;

  constructor(
    private usersService: UsersService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });

    this.usersService.getProfile().subscribe({
      next: (data) => {
        this.user = data;
        console.log('User data loaded:', this.user);


        if (this.user) {
          console.log('Setting form values:', {
            username: this.user.username,
            email: this.user.email
          });

          this.profileForm.setValue({
            username: this.user.username || '',
            email: this.user.email || ''
          });
        }
      },
      error: (err) => {
        console.error('Error loading user data:', err);
        this.error = err;
      }
    });
  }

  openProfileModal(): void {
    if (this.user) {
      console.log('Opening modal with user data:', this.user);
      this.profileForm.setValue({
        username: this.user.username || '',
        email: this.user.email || ''
      });

      this.errorMessage = null;
      this.showProfileModal = true;
    }
  }

  closeProfileModal(event: MouseEvent): void {
    if (
      event.target === event.currentTarget ||
      (event.target as HTMLElement).classList.contains("close-modal") ||
      (event.target as HTMLElement).classList.contains("cancel-btn")
    ) {
      this.showProfileModal = false;
    }
  }

  updateProfile(event: Event): void {
    event.preventDefault();

    if (this.profileForm.invalid) {
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }


    this.usersService.updateProfile(this.profileForm.value).subscribe({
      next: (updatedUser) => {
        console.log('Profile updated successfully:', updatedUser);
        this.user = {...this.user, ...updatedUser};
        this.showProfileModal = false;
      },
      error: (err) => {
        console.error('Error updating profile:', err);
        this.errorMessage = 'Failed to update profile. Please try again.';
      }
    });
  }

  openAvatarModal(): void {
    this.selectedAvatar = this.user?.profilePicture || null;
    this.showAvatarModal = true;
  }

  closeAvatarModal(event: MouseEvent): void {
    if (
      event.target === event.currentTarget ||
      (event.target as HTMLElement).classList.contains("close-modal") ||
      (event.target as HTMLElement).classList.contains("cancel-btn")
    ) {
      this.showAvatarModal = false;
    }
  }

  selectAvatar(avatar: string): void {
    this.selectedAvatar = avatar;
    this.uploadedFile = null;
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.uploadedFile = input.files[0];
      this.selectedAvatar = null;
    }
  }

  updateAvatar(): void {
    if (!this.selectedAvatar && !this.uploadedFile) {
      console.error('No avatar selected or file uploaded!');
      return;
    }

    const formData = new FormData();

    if (this.uploadedFile) {
      formData.append('profilepicture', this.uploadedFile); // Send uploaded file
      this.sendAvatarUpdate(formData);
    } else if (this.selectedAvatar) {
      this.convertUrlToFile(this.selectedAvatar).then((file) => {
        formData.append('profilepicture', file); // Convert URL to file
        this.sendAvatarUpdate(formData);
      }).catch(error => console.error('Error converting URL to file:', error));
    }
  }

  sendAvatarUpdate(formData: FormData): void {
    this.usersService.updateAvatar(formData).subscribe({
      next: (response) => {
        console.log('Avatar updated successfully:', response);
        if (this.user) {
          this.user.profilePicture = response.profilePicture; // Update UI
        }
        this.showAvatarModal = false;
      },
      error: (err) => {
        console.error('Error updating avatar:', err);
      }
    });
  }

  convertUrlToFile(imageUrl: string): Promise<File> {
    return fetch(imageUrl)
      .then(response => response.blob())
      .then(blob => new File([blob], 'avatar.png', {type: blob.type}));
  }

}
