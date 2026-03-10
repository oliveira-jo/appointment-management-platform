import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/auth/auth.service';
import { RoundLayoutComponent } from "./layout/round-layout/round-layout.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RoundLayoutComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';

  constructor(private authService: AuthService) { }

  isLogged(): boolean {
    return this.authService.isAuthenticated();
  }

}
