import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddConfigurationsComponent } from './add-configurations.component';

describe('AddConfigurationsComponent', () => {
  let component: AddConfigurationsComponent;
  let fixture: ComponentFixture<AddConfigurationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddConfigurationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddConfigurationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
