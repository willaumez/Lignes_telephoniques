import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { TypeAttributService } from './type-attribut.service';

describe('TypeAttributService', () => {
  let service: TypeAttributService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(TypeAttributService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
