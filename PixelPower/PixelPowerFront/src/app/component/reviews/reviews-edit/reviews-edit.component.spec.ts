import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsEditComponent } from './reviews-edit.component';

describe('ReviewEditComponent', () => {
  let component: ReviewsEditComponent;
  let fixture: ComponentFixture<ReviewsEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewsEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
