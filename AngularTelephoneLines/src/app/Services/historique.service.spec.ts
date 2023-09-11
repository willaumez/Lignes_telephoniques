import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { HistoriqueService } from './historique.service';

describe('HistoriqueService', () => {
  let service: HistoriqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(HistoriqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
