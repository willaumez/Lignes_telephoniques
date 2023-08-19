import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminTemplateComponent} from "./Components/admin-template/admin-template.component";
import {LoginComponent} from "./Components/login/login.component";
import {AuthenticationGuard} from "./guards/authentication.guard";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {
    path: 'admin',
    component: AdminTemplateComponent,
    canActivate: [AuthenticationGuard],
    children: [
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
