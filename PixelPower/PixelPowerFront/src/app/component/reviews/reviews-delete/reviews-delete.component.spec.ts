import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsDeleteComponent } from './reviews-delete.component';

describe('ReviewDeleteComponent', () => {
  let component: ReviewsDeleteComponent;
  let fixture: ComponentFixture<ReviewsDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewsDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewsDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
