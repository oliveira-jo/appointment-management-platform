import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  email: string | null = "";

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.email = this.authService.getEmail();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}