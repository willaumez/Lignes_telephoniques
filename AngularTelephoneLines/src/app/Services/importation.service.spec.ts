import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { ImportationService } from './importation.service';

describe('ImportationService', () => {
  let service: ImportationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(ImportationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
