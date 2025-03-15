import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GamecomparaisonComponent } from './gamecomparaison.component';

describe('GamecomparaisonComponent', () => {
  let component: GamecomparaisonComponent;
  let fixture: ComponentFixture<GamecomparaisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GamecomparaisonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GamecomparaisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
