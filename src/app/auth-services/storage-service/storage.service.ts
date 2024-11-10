import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private readonly TOKEN = 'c_token';
  private readonly USER = 'c_user';

  constructor() {}

  // Check if token exists
  hasToken(): boolean {
    const token = this.getToken();
    return token !== null && token !== '';
  }

  // Save token to localStorage
  saveToken(token: string): void {
    window.localStorage.setItem(this.TOKEN, token);
  }

  // Get token from localStorage
  getToken(): string {
    return localStorage.getItem(this.TOKEN) || "";
  }

  // Save user to localStorage
  saveUser(user: any): void {
    window.localStorage.setItem(this.USER, JSON.stringify(user));
  }

  // Get user object from localStorage
  getUser(): any {
    const user = localStorage.getItem(this.USER);
    return user ? JSON.parse(user) : null;
  }

  // Get user ID
  getUserId(): string {
    const user = this.getUser();
    return user ? user.userId : '';
  }

  // Get user role
  getUserRole(): string {
    const user = this.getUser();
    return user ? user.role : '';
  }

  // Check if user is logged in
  isUserLoggedIn(): boolean {
    return this.hasToken();
  }

  // Check if admin is logged in
  isAdminLoggedIn(): boolean {
    const role: string = this.getUserRole();
    return this.isUserLoggedIn() && role === '1';
  }

  // Clear user data
  signOut(): void {
    window.localStorage.removeItem(this.TOKEN);
    window.localStorage.removeItem(this.USER);
  }
}
