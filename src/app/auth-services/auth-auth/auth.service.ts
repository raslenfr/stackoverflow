import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { StorageService } from '../storage-service/storage.service';

const BASIC_URL =['http://localhost:8083']
export const AUTH_HEADER = "authorization"
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient , private storage: StorageService ) { }

  signup(signupRequest: any):Observable<any>{
    return this.http.post(BASIC_URL+"/sign-up",signupRequest);
  }

  login(email:string , password:string):Observable<any>{
    return this.http.post(BASIC_URL+"/authentication",{
  email,
  password
    },
       {observe:'response'})
    .pipe(
      tap(() => this.log("User Authentication")), // Changed 'this.log' to 'console.log' for logging
      map((res: HttpResponse<any>) => {
        // Save the user data
        this.storage.saveUser(res.body);

        const tokenLength = res.headers.get(AUTH_HEADER)?.length || 0;
        const bearerToken = res.headers.get(AUTH_HEADER)?.substring(7, tokenLength);

        if (bearerToken) {
          this.storage.saveToken(bearerToken);
        }
        return res;
      })
    );
}
log(message:string):void{
  console.log("User Auth Service" + message ) 

}
}