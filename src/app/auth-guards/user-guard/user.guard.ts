import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../../auth-services/storage-service/storage.service";
import { MatSnackBar } from "@angular/material/snack-bar";




@Injectable({
  providedIn: 'root'
 })
 export class userGuard implements CanActivate{
   
   constructor(private router:Router, private snackBar: MatSnackBar,private storageService: StorageService) { }
   
   canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.storageService.hasToken()) {  // Use `this.storageService.hasToken()`
      this.storageService.signOut();        // Use `this.storageService.signOut()`
      this.router.navigateByUrl("/login");
      this.snackBar.open("You are not logged-in, please log in first.", "Close", 
        { duration: 5000 }
      );
      return false;
    }
    return true;
  }
}