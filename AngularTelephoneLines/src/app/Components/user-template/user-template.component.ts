import { Component } from '@angular/core';
import {LoginService} from "../../Services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-template',
  templateUrl: './user-template.component.html',
  styleUrls: ['./user-template.component.scss']
})
export class UserTemplateComponent {

  constructor(public loginService: LoginService, public router: Router) {
  }

  handleLogOut() {
    let conf = confirm("Êtes-vous sûr de vouloir vous déconnecter?")
    if (!conf) return;
    this.loginService.logout();
    this.router.navigateByUrl("/login");
  }


}
