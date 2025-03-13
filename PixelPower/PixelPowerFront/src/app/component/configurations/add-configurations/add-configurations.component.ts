import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ConfigurationsService} from '../../../service/configurations.service';


@Component({
  selector: 'app-add-configurations',
  standalone: false,
  templateUrl: './add-configurations.component.html',
  styleUrl: './add-configurations.component.css'
})
export class AddConfigurationsComponent {
  configurationForm: FormGroup;
  osOptions = ['WINDOWS', 'MACOS', 'LINUX'];
  statusOptions = ['PRIMARY', 'SECONDARY'];
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private configService: ConfigurationsService) {
    this.configurationForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      cpu: ['', [Validators.required, Validators.pattern(/^(Intel|AMD)\s+[a-zA-Z0-9\s\-\+]+$/)]],
      gpu: ['', [Validators.required, Validators.pattern(/^(NVIDIA|AMD|Intel)\s+[a-zA-Z0-9\s\-\+]+$/)]],
      ram: ['', [Validators.required, Validators.pattern(/^(4|8|16|32|64|128)GB$/)]],
      storage: ['', [Validators.required, Validators.pattern(/^(128|256|512|1024|2048)GB$|^(1|2|4|8)TB$/)]],
      os: ['', Validators.required],
      status: ['', Validators.required]
    });
  }

  submitForm() {
    if (this.configurationForm.valid) {
      this.configService.addConfiguration(this.configurationForm.value).subscribe({
        next: (response) => {
          this.successMessage = 'Configuration added successfully!';
          this.errorMessage = '';
          this.configurationForm.reset();
        },
        error: (err) => {
          this.successMessage = '';
          this.errorMessage = 'Failed to add configuration. Please check your inputs.';
          console.error(err);
        }
      });
    }
  }
}
