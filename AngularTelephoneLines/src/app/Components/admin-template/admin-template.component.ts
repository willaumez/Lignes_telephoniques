import { Component } from '@angular/core';
import {LoginService} from "../../Services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrls: ['./admin-template.component.scss']
})
export class AdminTemplateComponent {

  constructor(public loginService: LoginService, public router: Router) {
  }

  handleLogOut() {
    let conf = confirm("Êtes-vous sûr de vouloir vous déconnecter?")
    if (!conf) return;
    this.loginService.logout();
    this.router.navigateByUrl("/login");
  }


}
