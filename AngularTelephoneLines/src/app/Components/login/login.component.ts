import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../Services/login.service";
import {Router} from "@angular/router";
import {CoreService} from "../../core/core.service";
import {RoleType} from "../../Models/User";


// Fonction validateur personnalisée
function emailOrNameValidator(control: FormControl): { [key: string]: any } | null {
  const value: string = control.value;

  // Vérifier si la valeur est une adresse email valide ou un nom (au moins 3 caractères)
  if (Validators.email(control) && Validators.email(control) !== null) {
    return null; // Valide si c'est une adresse email valide
  } else if (value && value.trim().length >= 3) {
    return null; // Valide si c'est un nom valide (au moins 3 caractères)
  } else {
    return { invalidUsername: true }; // Sinon, retourne une erreur
  }
}


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit{
  formLogin! : FormGroup;
  hide: boolean = true;
  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router, private _coreService: CoreService) {
  }
  ngOnInit(): void {
    this.formLogin= this.fb.group({
      username : new FormControl('',[Validators.required, emailOrNameValidator]),
      password : new FormControl('', [Validators.required, Validators.minLength(5)]),
    })
  }

  handleLogin() {
    let username = this.formLogin.value.username;
    let password = this.formLogin.value.password;

    if (username && password) {
      this.loginService.login(username, password).subscribe({
        next: data => {
          this.loginService.loadProfile(data['access-token']);
          if (this.loginService.isAuthenticated) {
            const userRole = this.loginService.getUserData().role;
            if (userRole === RoleType.ADMIN) {
              this.router.navigateByUrl("/admin");
            } else if (userRole === RoleType.USER) {
              this.router.navigateByUrl("/user");
            }
          }

          this._coreService.openSnackBar('Connexion réussie ! ');
        },
        error: err => {
          this._coreService.openSnackBar('Entrer un e-mail et un mot de passe valide! ');
        }
      });
    }
  }






}
