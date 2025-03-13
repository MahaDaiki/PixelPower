import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayConfigurationsComponent } from './display-configurations.component';

describe('DisplayConfigurationsComponent', () => {
  let component: DisplayConfigurationsComponent;
  let fixture: ComponentFixture<DisplayConfigurationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisplayConfigurationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayConfigurationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
