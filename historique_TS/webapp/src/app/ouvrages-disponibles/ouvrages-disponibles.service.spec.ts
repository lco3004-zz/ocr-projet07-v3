import {TestBed} from '@angular/core/testing';

import {OuvragesDisponiblesService} from './ouvrages-disponibles.service';

describe('OuvragesDisponiblesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OuvragesDisponiblesService = TestBed.get(OuvragesDisponiblesService);
    expect(service).toBeTruthy();
  });
});
