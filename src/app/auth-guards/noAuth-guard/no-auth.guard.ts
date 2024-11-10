import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../../auth-services/storage-service/storage.service";

@Injectable({
 providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {
  
  constructor(private router: Router,private storageService: StorageService ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.storageService.hasToken()) { 
      this.router.navigateByUrl("/user/dashboard");
      return false;
    }
    return true;
  }
}
