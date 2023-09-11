import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { RapprochementService } from './rapprochement.service';

describe('RapprochementService', () => {
  let service: RapprochementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(RapprochementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
