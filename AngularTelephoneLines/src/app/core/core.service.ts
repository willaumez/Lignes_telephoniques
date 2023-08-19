import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root',
})
export class CoreService {
  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(private _snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string= 'ok') {
    this._snackBar.open(message, action, {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: 5000,
      //panelClass: 'custom-snackbar'
    });
  }
}
