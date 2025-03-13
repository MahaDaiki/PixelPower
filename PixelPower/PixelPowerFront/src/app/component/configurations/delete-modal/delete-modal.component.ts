import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ConfigurationsService} from '../../../service/configurations.service';

@Component({
  selector: 'app-delete-modal',
  standalone: false,
  templateUrl: './delete-modal.component.html',
  styleUrl: './delete-modal.component.css'
})
export class DeleteModalComponent {
  @Input() showModal: boolean = false;
  @Input() configId: string | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() deleted = new EventEmitter<void>();

  loading = false;
  error: string | null = null;

  constructor(private configurationService: ConfigurationsService) {}

  deleteConfiguration() {
    if (this.configId) {
      this.loading = true;
      this.configurationService.deleteConfiguration(this.configId).subscribe(
        () => {
          this.deleted.emit();
          this.closeModal();
        },
        (error) => {
          this.error = 'Failed to delete configuration';
          this.loading = false;
        }
      );
    }
  }

  closeModal() {
    this.close.emit();
  }
}
