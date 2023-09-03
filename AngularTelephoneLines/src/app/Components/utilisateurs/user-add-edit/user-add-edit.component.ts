import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../Services/user.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CoreService} from "../../../core/core.service";
import {User} from "../../../Models/User";
import {LoginService} from "../../../Services/login.service";


interface Selection {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-user-add-edit',
  templateUrl: './user-add-edit.component.html',
  styleUrls: ['./user-add-edit.component.scss']
})
export class UserAddEditComponent implements OnInit{

  showPasswordFields = false;
  hide: boolean = true;
  userForm: FormGroup;
  userData : User = this.loginService.getUserData();


  roles: Selection[] = [
    {value: 'USER', viewValue: 'UTILISATEUR'},
    {value: 'ADMIN', viewValue: 'ADMINISTRATEUR'},
  ]


  constructor(private _fb: FormBuilder, private _userService: UserService,
              private _dialogRef: MatDialogRef<UserAddEditComponent>, private _coreService: CoreService,
              @Inject(MAT_DIALOG_DATA) public data: any, public loginService: LoginService) {

    this.userForm = this._fb.group({
      id: null,
      username: ['', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6)]],
      password2: [null, [Validators.required, Validators.minLength(6)]],
      role: ['', [Validators.required]],
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.userForm.patchValue(this.data);
      this.userForm.patchValue({
        password: null,
      });
      this.userForm.updateValueAndValidity();
    }
  }

  onFormSubmit() {
    // Si l'action est une création d'utilisateur
    if (!this.data){
      if (this.userForm.valid){
        let user: User = this.userForm.value;
        this._userService.saveUser(user, this.userData.username).subscribe({
          next: (val: any) => {
            this._coreService.openSnackBar('Utilisateur ajouté avec succès !');
            this._dialogRef.close(true);
          },
          error: (err: any) => {
            this._dialogRef.close(true);
            this._coreService.openSnackBar('Erreur: '+err.error.message);
          }
        });
      }else {
        this._coreService.openSnackBar('Valider correctement le formulaire avant de soumettre !');
        return;
      }
    }else {
      if (this.showPasswordFields && this.userForm.valid){
        let user: User = this.userForm.value;
        this._userService.updateUser(user, this.userData.username).subscribe({
          next: (val: any) => {
            this._dialogRef.close(true);
            this._coreService.openSnackBar('Utilisateur mise à jour avec succès !');
          },
          error: (err: any) => {
            this._coreService.openSnackBar('Erreur: '+err.error.message);
            this._dialogRef.close(true);
          }
        });
      } else if (!this.showPasswordFields){
        // Supprimer temporairement les validateurs des champs de mot de passe lors de la mise à jour
        this.userForm.get('password')?.clearValidators();
        this.userForm.get('password')?.updateValueAndValidity();
        this.userForm.get('password2')?.clearValidators();
        this.userForm.get('password2')?.updateValueAndValidity();

        if (this.userForm.valid){
          let user: User = this.userForm.value;
          this._userService.updateUser(user, this.userData.username).subscribe({
            next: (val: any) => {
              this._dialogRef.close(true);
              this._coreService.openSnackBar('Utilisateur mise à jour avec succès !');
            },
            error: (err: any) => {
              this._coreService.openSnackBar('Le Nom et l\'E-mail doivent être uniques !');
              this._dialogRef.close(true);
            }
          });
        }
        else {
          this._coreService.openSnackBar('Valider correctement le formulaire avant de soumettre !');
          return;
        }
        // Ajouter à nouveau les validateurs après la mise à jour
        this.userForm.get('password')?.setValidators([Validators.required, Validators.minLength(6)]);
        this.userForm.get('password')?.updateValueAndValidity();
        this.userForm.get('password2')?.setValidators([Validators.required, Validators.minLength(6)]);
        this.userForm.get('password2')?.updateValueAndValidity();
      }
      else {
        this._coreService.openSnackBar('Valider correctement le formulaire avant de soumettre !');
        return;
      }

    }

  }


  getName(): string {
    return this.userForm.value.username;
  }

  onResetPasswordClick() {

  }


  // Fonction de validation personnalisée pour vérifier l'égalité des mots de passe
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

  togglePasswordFields() {
    this.userForm.patchValue({
      password: null,
      password2: null
    });
    this.userForm.updateValueAndValidity();
    this.showPasswordFields = !this.showPasswordFields;
  }





}
