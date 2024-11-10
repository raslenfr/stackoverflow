import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})

export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  isSubmitting: boolean = false;  // To track the form submission status

  constructor(private fb: FormBuilder, private http: HttpClient , private snackBar: MatSnackBar , private router: Router) {}

  ngOnInit() {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { 
      // Cross-field validation for confirmPassword
      validators: this.passwordMatchValidator 
    });
  }

  // Cross-field validator
  passwordMatchValidator(formGroup: FormGroup): { [key: string]: boolean } | null {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password && confirmPassword && password !== confirmPassword ? { 'mismatch': true } : null;
  }

  signup() {
    if (this.signupForm.valid) {
      const signupData = this.signupForm.value;
      console.log('Form Data:', signupData);  

      // Set headers
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      });
      

      // Start submission
      this.isSubmitting = true;

      this.http.post('http://localhost:8083/sign-up', signupData, { headers }).subscribe(
        (response:any) => {
          console.log('Signup successful', response);
          alert('Signup successful!');  // You can use a modal or another way to notify the user
          this.isSubmitting = false;
        
          if (response.id != null) {
            // Success message and redirect
            this.snackBar.open("You're registered successfully!", 'Close', { duration: 5000 });
            this.router.navigateByUrl('/login');
          } else {
            // Show specific error message if provided by the backend
            this.snackBar.open(response.message , 'Close', { duration: 5000 });
          }
        
        
        
        
        },
        (error) => {
          console.error('Signup error', error);
          alert('Signup failed! Please try again.');
          this.isSubmitting = false;
        }
      );
    } else {
      alert('Please fill in all the required fields correctly.');
    }
  }
}
