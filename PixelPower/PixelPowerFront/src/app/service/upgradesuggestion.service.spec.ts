import { TestBed } from '@angular/core/testing';

import { UpgradesuggestionService } from './upgradesuggestion.service';

describe('UpgradesuggestionService', () => {
  let service: UpgradesuggestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpgradesuggestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
