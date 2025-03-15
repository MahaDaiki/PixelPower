import { TestBed } from '@angular/core/testing';

import { GamecomparaisonService } from './gamecomparaison.service';

describe('GamecomparaisonService', () => {
  let service: GamecomparaisonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GamecomparaisonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
