import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { LigneTelephoniqueService } from './ligne-telephonique.service';

describe('LigneTelephoniqueService', () => {
  let service: LigneTelephoniqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(LigneTelephoniqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
