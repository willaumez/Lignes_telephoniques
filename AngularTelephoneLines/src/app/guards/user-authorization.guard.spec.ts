import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { userAuthorizationGuard } from './user-authorization.guard';
import {HttpClientModule} from "@angular/common/http";

describe('UserAuthorizationGuard', () => {
  let guard: userAuthorizationGuard; // Remplacez par le bon nom de classe si nécessaire
  let mockActivatedRouteSnapshot: any;
  let mockRouterStateSnapshot: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [userAuthorizationGuard], // Assurez-vous que le bon nom de classe est utilisé
    });
    guard = TestBed.inject(userAuthorizationGuard); // Assurez-vous que le bon nom de classe est utilisé
    mockActivatedRouteSnapshot = {}; // Vous pouvez ajouter des propriétés mockées si nécessaire
    mockRouterStateSnapshot = {}; // Vous pouvez ajouter des propriétés mockées si nécessaire
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should do something', () => {
    const result = guard.canActivate(mockActivatedRouteSnapshot, mockRouterStateSnapshot);
    // Votre logique de test ici. Par exemple :
    expect(result).toBeTrue();
  });
});
