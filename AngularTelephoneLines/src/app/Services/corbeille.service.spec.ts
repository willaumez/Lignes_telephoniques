import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { CorbeilleService } from './corbeille.service';

describe('CorbeilleService', () => {
  let service: CorbeilleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(CorbeilleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
