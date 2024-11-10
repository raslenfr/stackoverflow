import { Component } from '@angular/core';
import { AuthService } from '../../auth-services/auth-auth/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms'; 
import {Router} from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm! :FormGroup;

constructor(
  private service: AuthService,
  private fb:FormBuilder ,
  private router:Router,
  private snackBar:MatSnackBar
){}
ngOnInit(): void {
  this.loginForm = this.fb.group({
    email:['', Validators.required],
    password:['', Validators.required],

  }) 
}
login() {
  console.log(this.loginForm.value);
  this.service.login(
    
    
    this.loginForm.get(['email'])!.value,
    this.loginForm.get(['password'])!.value

  
  ).subscribe({
    next: (response) => {
      console.log(response);
      this.router.navigateByUrl("user/dashboard");
    },
    error: () => {
      this.snackBar.open('Bad credentials', 'Close', {
        duration: 5000,
        panelClass: 'error-snackbar',
      });
    },
  });
}
}
