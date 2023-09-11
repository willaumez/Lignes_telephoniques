import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import {RoleType} from "../Models/User";
import {LoginService} from "../Services/login.service";
import {Injectable} from "@angular/core";


@Injectable({
  providedIn: 'root',
})
export class adminAuthorizationGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.userData) {
      const userRole = this.loginService.userData.role;
      if (userRole === RoleType.ADMIN) {
        return true;
      } else {
        this.router.navigateByUrl("/notAuthorized");
      }
    }
    return false;
  }
}
