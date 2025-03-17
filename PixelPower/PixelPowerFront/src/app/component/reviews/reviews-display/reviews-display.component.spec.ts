import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsDisplayComponent } from './reviews-display.component';

describe('ReviewsDisplayComponent', () => {
  let component: ReviewsDisplayComponent;
  let fixture: ComponentFixture<ReviewsDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewsDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewsDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
