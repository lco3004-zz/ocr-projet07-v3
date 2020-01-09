import {TestBed} from '@angular/core/testing';

import {PretsUsagerService} from './prets-usager.service';

describe('PretsUsagerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PretsUsagerService = TestBed.get(PretsUsagerService);
    expect(service).toBeTruthy();
  });
});
