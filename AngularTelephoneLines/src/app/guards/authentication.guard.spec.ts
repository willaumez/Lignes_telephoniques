import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationGuard } from './authentication.guard';

describe('AuthenticationGuard', () => {
  let authenticationGuard: AuthenticationGuard;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(() => {
    mockRouter = jasmine.createSpyObj('Router', ['parseUrl']);

    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        AuthenticationGuard,
        { provide: Router, useValue: mockRouter }
      ]
    });

    authenticationGuard = TestBed.inject(AuthenticationGuard);
  });

  it('should be created', () => {
    expect(authenticationGuard).toBeTruthy();
  });

  it('should allow activation when authenticated', () => {
    spyOn(authenticationGuard, 'canActivate').and.returnValue(true);

    const canActivateResult = authenticationGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    expect(canActivateResult).toBe(true);
  });

  it('should block activation and redirect when not authenticated', () => {
    spyOn(authenticationGuard, 'canActivate').and.returnValue(false);

    const canActivateResult = authenticationGuard.canActivate(
      {} as ActivatedRouteSnapshot,
      {} as RouterStateSnapshot
    );

    expect(canActivateResult).toBe(false);
    expect(mockRouter.parseUrl).toHaveBeenCalledWith('login');
  });
});
