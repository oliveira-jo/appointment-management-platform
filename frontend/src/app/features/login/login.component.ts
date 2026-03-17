import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  email = "";
  password = "";

  successMessage = '';
  errorMessage = '';
  successToast: any;
  errorToast: any;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  login() {

    this.authService.login({

      email: this.email,
      password: this.password

    }).subscribe({
      next: (response) => {
        this.showSuccess("Login efetuado com sucesso");
        this.authService.saveToken(response.accessToken);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.showError('Erro ao efetuar o login. Tente novamente.');
      }
    });
  }

  ngAfterViewInit() {
    const successEl = document.getElementById('successToast');
    const errorEl = document.getElementById('errorToast');

    this.successToast = new (window as any).bootstrap.Toast(successEl, {
      delay: 6000
    });

    this.errorToast = new (window as any).bootstrap.Toast(errorEl, {
      delay: 4000
    });
  }

  showSuccess(message: string) {
    this.successMessage = message;
    this.successToast.show();
  }

  showError(message: string) {
    this.errorMessage = message;
    this.errorToast.show();
  }

}

