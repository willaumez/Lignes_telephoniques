import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminTemplateComponent} from "./Components/admin-template/admin-template.component";
import {LoginComponent} from "./Components/login/login.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {UserTemplateComponent} from "./Components/user-template/user-template.component";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./Components/not-authorized/not-authorized.component";
import {AccueilComponent} from "./Components/accueil/accueil.component";
import {UtilisateursComponent} from "./Components/utilisateurs/utilisateurs.component";
import {RapprochementComponent} from "./Components/rapprochement/rapprochement.component";
import {ProfileComponent} from "./Components/profile/profile.component";
import {AideComponent} from "./Components/aide/aide.component";
import {HistoriquesComponent} from "./Components/historiques/historiques.component";
import {CorbeilleComponent} from "./Components/corbeille/corbeille.component";
import {TypeAttributComponent} from "./Components/type-attribut/type-attribut.component";
import {LignesTelephoniqueComponent} from "./Components/lignes-telephonique/lignes-telephonique.component";
import {TypeLigneComponent} from "./Components/type-ligne/type-ligne.component";
import {ParametreComponent} from "./Components/parametre/parametre.component";
import {adminAuthorizationGuard} from "./guards/admin-authorization.guard";
import {userAuthorizationGuard} from "./guards/user-authorization.guard";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {
    path: 'admin',
    component: AdminTemplateComponent,
    canActivate: [AuthenticationGuard, AuthorizationGuard, adminAuthorizationGuard], // Utilisation d'AuthenticationGuard et AuthorizationGuard
    children: [
      // Routes enfants pour l'administration si nécessaire
      {path: 'accueil', component: AccueilComponent},
      {path: 'lignes', component: LignesTelephoniqueComponent},
      {path: 'types-Ligne', component: TypeLigneComponent},
      {path: '', redirectTo: 'accueil', pathMatch: 'full'},

      {path: 'utilisateurs', component: UtilisateursComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'rapprochement', component: RapprochementComponent},
      {path: 'corbeille', component: CorbeilleComponent},
      {path: 'historique', component: HistoriquesComponent},
      {path: 'importation', component: ParametreComponent},
      {path: 'typeAttributs', component: TypeAttributComponent},
      {path: 'notAuthorized', component: NotAuthorizedComponent},
      {path: 'aide', component: AideComponent},
    ],
  },
  {
    path: 'user',
    component: UserTemplateComponent,
    canActivate: [AuthenticationGuard, AuthorizationGuard, userAuthorizationGuard], // Utilisation d'AuthenticationGuard et AuthorizationGuard
    children: [
      // Routes enfants pour les utilisateurs si nécessaire
      {path: 'accueil', component: AccueilComponent},
      {path: '', redirectTo: 'accueil', pathMatch: 'full'},
      {path: 'lignes', component: LignesTelephoniqueComponent},
      {path: 'types-Ligne', component: TypeLigneComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'rapprochement', component: RapprochementComponent},
      {path: 'aide', component: AideComponent},

      /*{path: 'list', component: LigneTelephoniqueComponent},
            {path: '', redirectTo: 'list', pathMatch: 'full'}, // Redirect to 'list' when accessing '/admin'
            {
              path: 'types',
              loadChildren: () => import('./types/types.module').then(m => m.TypesModule)
            },
            {path: 'users', component: UtilisateursComponent},
            {path: 'profile', component: ProfilComponent},
            {path: 'rapprochement', component: RapprochementComponent},
            {path: 'notAuthorized', component: NotAuthorizedComponent},*/

    ],
  },
  {
    path: 'notAuthorized', // Chemin pour la page "Non autorisée"
    component: NotAuthorizedComponent, // Composant pour la page "Non autorisée"
  },
  // Autres routes...
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
