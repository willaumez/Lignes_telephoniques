import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './Components/login/login.component';
import { AdminTemplateComponent } from './Components/admin-template/admin-template.component';
import { UserTemplateComponent } from './Components/user-template/user-template.component';
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatTableModule} from "@angular/material/table";
import {MatDividerModule} from "@angular/material/divider";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { NotAuthorizedComponent } from './Components/not-authorized/not-authorized.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {NgOptimizedImage} from "@angular/common";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatBadgeModule} from "@angular/material/badge";
import { AccueilComponent } from './Components/accueil/accueil.component';
import { UtilisateursComponent } from './Components/utilisateurs/utilisateurs.component';
import { ProfileComponent } from './Components/profile/profile.component';
import { RapprochementComponent } from './Components/rapprochement/rapprochement.component';
import { AideComponent } from './Components/aide/aide.component';
import { UserAddEditComponent } from './Components/utilisateurs/user-add-edit/user-add-edit.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatDialogModule} from "@angular/material/dialog";
import {MatChipsModule} from "@angular/material/chips";
import {MatSelectModule} from "@angular/material/select";
import {AppHttpInterceptor} from "./interceptors/app-http.interceptor";
import { HistoriquesComponent } from './Components/historiques/historiques.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import { CorbeilleComponent } from './Components/corbeille/corbeille.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import { TypeAttributComponent } from './Components/type-attribut/type-attribut.component';
import {MatTabsModule} from "@angular/material/tabs";
import { GestionTypeComponent } from './Components/type-attribut/gestion-type/gestion-type.component';
import { GestionAttributComponent } from './Components/type-attribut/gestion-attribut/gestion-attribut.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminTemplateComponent,
    UserTemplateComponent,
    NotAuthorizedComponent,
    AccueilComponent,
    UtilisateursComponent,
    ProfileComponent,
    RapprochementComponent,
    AideComponent,
    UserAddEditComponent,
    HistoriquesComponent,
    CorbeilleComponent,
    TypeAttributComponent,
    GestionTypeComponent,
    GestionAttributComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatTableModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatAutocompleteModule,
    MatToolbarModule,
    MatMenuModule,
    NgOptimizedImage,
    MatSidenavModule,
    MatListModule,
    MatBadgeModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatChipsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,
    MatTabsModule,
    MatSlideToggleModule,
    FormsModule

  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
