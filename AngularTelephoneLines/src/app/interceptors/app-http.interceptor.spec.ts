import { TestBed } from '@angular/core/testing';

import { AppHttpInterceptor } from './app-http.interceptor';
import {HttpClientModule} from "@angular/common/http";

describe('AppHttpInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientModule],
    providers: [
      AppHttpInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: AppHttpInterceptor = TestBed.inject(AppHttpInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
