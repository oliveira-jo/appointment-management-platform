import { Component, OnInit } from '@angular/core';
import { ProfessionalService } from '../../professional.service';
import { ProfessioanlRequest, ProfessioanlResponse } from '../../professional-model';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';

declare var bootstrap: any;

@Component({
  selector: 'app-professional-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './professional-list.component.html'
})
export class ProfessionalListComponent implements OnInit {

  professionals: ProfessioanlResponse[] = [];
  professional: ProfessioanlRequest = { name: '', email: '', phone: '' };

  searchEmail = '';
  successMessage = '';
  errorMessage = '';
  successToast: any;
  errorToast: any;


  selectedProfessionalId: string | null = null;
  editingId: string | null = null;

  constructor(private professionalService: ProfessionalService) { }

  ngOnInit(): void {
    this.loadProfessionals();
  }

  loadProfessionals() {

    this.professionalService.getAll()
      .subscribe((data: any) => {
        const list = this.professionals = data as ProfessioanlResponse[];
        this.professionals = list.filter(p => p.role !== 'ROLE_ADMIN');
      });

  }

  search() {

    if (!this.searchEmail) {
      this.loadProfessionals();
      return;
    }

    this.professionalService.getByEmail(this.searchEmail)
      .subscribe((data: any) => {
        this.professionals = [data as ProfessioanlResponse];
      });

  }

  save(form: any) {
    if (this.editingId) {
      this.professionalService.update(this.editingId, this.professional)
        .subscribe({
          next: () => {
            this.showSuccess("Sucesso na edição do profissional");
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao editar o profissional. Tente novamente.');
          }
        });

    } else {
      this.professionalService.create(this.professional)
        .subscribe({
          next: () => {
            this.showSuccess("Profissional criado com sucesso");
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao salvar o profissional. Tente novamente.');
          }
        });
    }

  }

  afterSave(form: any) {
    this.loadProfessionals();

    form.reset();

    this.professional = { name: '', email: '', phone: '' };

    this.editingId = null;

    const modal = bootstrap.Modal.getInstance(
      document.getElementById('professionalModal')
    );

    modal.hide();

  }

  change(id: string) {
    this.professionalService.getById(id)
      .subscribe((data) => {

        this.professional = {
          name: data.name,
          email: data.email,
          phone: data.phone
        };

        this.editingId = id;

        const modal = new bootstrap.Modal(
          document.getElementById('professionalModal')
        );

        modal.show();

      });
  }

  formatPhone() {
    let phone = this.professional.phone.replace(/\D/g, '');

    if (phone.length > 11) phone = phone.slice(0, 11);

    phone = phone
      .replace(/^(\d{2})(\d)/g, "($1) $2")
      .replace(/(\d)(\d{4})$/, "$1-$2");

    this.professional.phone = phone;
  }

  delete() {
    if (!this.selectedProfessionalId) return;

    this.professionalService.delete(this.selectedProfessionalId)
      .subscribe(() => {

        this.loadProfessionals();

        const modal = bootstrap.Modal.getInstance(
          document.getElementById('deleteModal')
        );

        modal.hide();

      });
  }

  openDeleteModal(id: string) {
    this.selectedProfessionalId = id;

    const modal = new bootstrap.Modal(
      document.getElementById('deleteModal')
    );

    modal.show();
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