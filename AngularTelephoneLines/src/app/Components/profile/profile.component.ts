import {Component, OnInit} from '@angular/core';
import {User} from "../../Models/User";
import {CoreService} from "../../core/core.service";
import {UserService} from "../../Services/user.service";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../Services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  errorMessage: string = "Aucune donnée utilisateur disponible!";
  userData: User | any;
  showPasswordEdit:boolean = false;
  showInfosEdit:boolean = false;
  passwordConfirmed:boolean = false;
  userInfosForm: FormGroup | any;
  userPwdForm: FormGroup | any;

  constructor(private loginService: LoginService, private _fb: FormBuilder, private _userService: UserService, private _coreService: CoreService) {
    this.userData = this.loginService.getUserData();

    this.userInfosForm = this._fb.group({
      id: this.userData.id,
      username: [this.userData.username, [Validators.required, Validators.minLength(4)]],
      email: [this.userData.email, [Validators.required, Validators.email]],
      password: [null],
      role: [this.userData.role, [Validators.required]],
    })

    this.userPwdForm = this._fb.group({
      id: this.userData.id,
      username: [this.userData.username, [Validators.required]],
      email: [this.userData.email, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6)]],
      password2: [null, [Validators.required, Validators.minLength(6)]],
      role: [this.userData.role, [Validators.required]],
    }, {
      validators: this.passwordMatchValidator
    });

  }

  showEditInfos(): void {
    this.showInfosEdit = !this.showInfosEdit;
    this.userInfosForm.patchValue({
      username: this.userData.username,
      email: this.userData.email,
    });
  }

  showChangePassword(): void {
    this.showPasswordEdit = !this.showPasswordEdit;
  }

  handleReset(): void {
    this.showInfosEdit = false;
    this.showPasswordEdit = false;
    this.passwordConfirmed = false;
  }

  private passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password')?.value;
    const password2 = control.get('password2')?.value;

    if (password !== password2) {
      control.get('password2')?.setErrors({'passwordMismatch': true});
      return {'passwordMismatch': true};
    } else {
      control.get('password2')?.setErrors(null);
      return null;
    }
  }

  onFormSubmitInfos(): void {
    let conf:boolean = confirm("Êtes-vous sûr, cela entraînera une reconnexion!")
    if (!conf) return;
    if (this.showInfosEdit) {
      if (this.userInfosForm.valid) {
        let user: User = this.userInfosForm.value;
        this._userService.updateUser(user, "profil: " + this.userData.username).subscribe({
          next: (val: any): void => {
            this._coreService.openSnackBar('Informations misent à jour avec succès !');
            this.loginService.logout();
          },
          error: (err: any): void => {
            this._coreService.openSnackBar('Échec de la modification des informations!');
            console.log(err);
          }
        });

      } else {
        this._coreService.openSnackBar('Valider correctement le formulaire avant de soumettre !');
        return;
      }
    }
  }

  onFormSubmitPwd(): void {
    if (this.showPasswordEdit) {
      if (!this.passwordConfirmed) {
        let password: string = this.userPwdForm.value.password;
        this._userService.confirmPassword(this.userData.id, password).subscribe({
          next: (val: any): void => {
            console.log("this._userService.confirmPassword--" + val);
            if (val) {
              this._coreService.openSnackBar('Mot de passe confirmé!\n Entrer le nouveau mot de passe!');
              this.userPwdForm.patchValue({
                password: null,
                password2: null,
              });
              this.passwordConfirmed = true;
            } else {
              this._coreService.openSnackBar('Mot de passe incorrecte!  Réessayer!');
              this.passwordConfirmed = false;
            }
          },
          error: (err: any): void => {
            console.log(err);
            this._coreService.openSnackBar('Mot de passe incorrecte!  Réessayer!');
          }
        });
      }
      if (this.passwordConfirmed) {
        if (this.userPwdForm.valid) {
          let conf:boolean = confirm("Êtes-vous sûr, cela entraînera une reconnexion!")
          if (!conf) return;
          let user: User = this.userPwdForm.value;
          console.log("User----  ", user);
          this._userService.updateUser(user, "profil: " + this.userData.username).subscribe({
            next: (val: any): void => {
              this._coreService.openSnackBar('Mot de pass modifié avec succès !');
              this.loginService.logout();
            },
            error: (err: any): void => {
              this._coreService.openSnackBar("Erreur changement de mot de passe");
            }
          });
        } else {
          this._coreService.openSnackBar('Valider correctement le formulaire avant de soumettre !');
          this.userPwdForm.patchValue({
            password: null,
            password2: null,
          });
          return;
        }
      }

    }
  }


}
