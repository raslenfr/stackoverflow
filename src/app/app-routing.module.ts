import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth-component/login/login.component';
import { SignupComponent } from './auth-component/signup/signup.component';
import { NoAuthGuard } from './auth-guards/noAuth-guard/no-auth.guard';
import { userGuard } from './auth-guards/user-guard/user.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },
  { path: 'signup', component: SignupComponent, canActivate: [NoAuthGuard] },
  { 
    path: 'user', 
    loadChildren: () => import('./user/user.module').then(m => m.UserModule), 
    canActivate: [userGuard] // Protect the 'user' routes with the userGuard
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect root to login
  { path: '**', redirectTo: '/login' } // Redirect any unknown paths to login
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
