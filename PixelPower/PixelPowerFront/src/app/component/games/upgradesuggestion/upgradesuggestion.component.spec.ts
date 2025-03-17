import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpgradesuggestionComponent } from './upgradesuggestion.component';

describe('UpgradesuggestionComponent', () => {
  let component: UpgradesuggestionComponent;
  let fixture: ComponentFixture<UpgradesuggestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpgradesuggestionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpgradesuggestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
