import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../Services/login.service';
import {RoleType} from "../Models/User";

@Injectable({
  providedIn: 'root'
})
/*export class AuthorizationGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.userData && this.loginService.userData.role === RoleType.ADMIN) {
      this.router.navigateByUrl("/admin");
      return true;
    } else if (this.loginService.userData && this.loginService.userData.role === RoleType.USER) {
      this.router.navigateByUrl("/user");
      return true;
    } else {
      this.router.navigateByUrl("/notAuthorized");
      return true;
    }
  }
}*/

export class AuthorizationGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.userData) {
      const userRole = this.loginService.userData.role;

      if (userRole === RoleType.ADMIN) {
        return true;
      } else if (userRole === RoleType.USER) {
        return true;
      } else {
        this.router.navigateByUrl("/notAuthorized");
        return false;
      }
    }
    return false;
  }
}

