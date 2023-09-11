import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Injectable} from "@angular/core";
import {LoginService} from "../Services/login.service";
import {Observable} from "rxjs";
import {RoleType} from "../Models/User";

@Injectable({
  providedIn: 'root',
})
export class userAuthorizationGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.userData) {
      const userRole = this.loginService.userData.role;
      if (userRole === RoleType.USER) {
        return true;
      } else {
        this.router.navigateByUrl("/notAuthorized");
      }
    }
    return false;
  }
}
