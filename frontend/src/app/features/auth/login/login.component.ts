import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  email = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) { }

  onSubmit() {
    this.auth.login({
      email: this.email,
      password: this.password
    }).subscribe(() => {
      this.router.navigate(['/appointments']);
    });
  }
}