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

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {
    path: 'admin',
    component: AdminTemplateComponent,
    canActivate: [AuthenticationGuard, AuthorizationGuard], // Utilisation d'AuthenticationGuard et AuthorizationGuard
    children: [
      // Routes enfants pour l'administration si nécessaire
      {path: 'accueil', component: AccueilComponent},
      {path: '', redirectTo: 'accueil', pathMatch: 'full'},

      {path: 'utilisateurs', component: UtilisateursComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'rapprochement', component: RapprochementComponent},
      {path: 'notAuthorized', component: NotAuthorizedComponent},
      {path: 'aide', component: AideComponent},
    ],
  },
  {
    path: 'user',
    component: UserTemplateComponent,
    canActivate: [AuthenticationGuard, AuthorizationGuard], // Utilisation d'AuthenticationGuard et AuthorizationGuard
    children: [
      // Routes enfants pour les utilisateurs si nécessaire
      {path: 'accueil', component: AccueilComponent},
      {path: '', redirectTo: 'accueil', pathMatch: 'full'},

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
