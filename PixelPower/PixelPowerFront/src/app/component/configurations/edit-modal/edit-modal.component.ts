import {Component, EventEmitter, Input, Output} from '@angular/core';

import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfigurationsService} from '../../../service/configurations.service';

@Component({
  selector: 'app-edit-modal',
  standalone: false,
  templateUrl: './edit-modal.component.html',
  styleUrl: './edit-modal.component.css'
})
export class EditModalComponent {
  @Input() showModal: boolean = false;
  @Input() configId: string | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() updated = new EventEmitter<any>();

  editForm: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private configurationService: ConfigurationsService
  ) {
    this.editForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      cpu: ['', [Validators.required]],
      gpu: ['', [Validators.required]],
      ram: ['', [Validators.required]],
      storage: ['', [Validators.required]],
      os: ['', [Validators.required]],
      status: ['', [Validators.required]],
    });
  }

  ngOnChanges() {
    if (this.configId) {
      this.loadConfiguration(this.configId);
    }
  }

  loadConfiguration(id: string) {
    this.configurationService.getConfigurationById(id).subscribe(
      (config) => {
        this.editForm.patchValue(config);
      },
      (error) => {
        this.error = 'Failed to load configuration details';
      }
    );
  }

  updateConfiguration() {
    if (this.editForm.invalid) {
      return;
    }

    this.loading = true;
    const updatedConfig = this.editForm.value;

    if (this.configId) {
      this.configurationService.updateConfiguration(this.configId, updatedConfig).subscribe({
        next: (config) => {
          this.updated.emit(config);
          this.closeModal();
        },
        error: () => {
          this.error = 'Failed to update configuration';
          this.loading = false;
        }
      });
    }
  }


  closeModal() {
    this.close.emit();
  }
}
