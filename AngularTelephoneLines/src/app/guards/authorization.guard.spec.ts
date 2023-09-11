import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

import { AuthorizationGuard } from './authorization.guard';
import {HttpClientModule} from "@angular/common/http";

describe('AuthorizationGuard', () => {
  let authorizationGuard: AuthorizationGuard;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(() => {
    mockRouter = jasmine.createSpyObj('Router', ['navigateByUrl']);

    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        AuthorizationGuard,
        { provide: Router, useValue: mockRouter }
      ]
    });

    authorizationGuard = TestBed.inject(AuthorizationGuard);
  });

  it('should be created', () => {
    expect(authorizationGuard).toBeTruthy();
  });

  it('should allow activation when authorized', () => {
    spyOn(authorizationGuard, 'canActivate').and.returnValue(true);

    const canActivateResult = authorizationGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    expect(canActivateResult).toBe(true);
  });

  it('should block activation and navigate to unauthorized page when not authorized', () => {
    spyOn(authorizationGuard, 'canActivate').and.returnValue(false);

    const canActivateResult = authorizationGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    expect(canActivateResult).toBe(false);
    expect(mockRouter.navigateByUrl).toHaveBeenCalledWith('unauthorized');
  });
});
