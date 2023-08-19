import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { LoginService } from "../Services/login.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (this.loginService.isAuthenticated) {
      return true;
    } else {
      // Vous pouvez rediriger vers la page de connexion ou une autre page si l'utilisateur n'est pas authentifié.
      return this.router.parseUrl('login');
    }
  }
}
