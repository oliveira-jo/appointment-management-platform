import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, registerLocaleData } from '@angular/common';
import { ProductRequest, ProductResponse } from '../../product-modal';
import { ProductService } from '../../product.service';
import { DurationPipe } from '../../../../../core/pipes/duration.pipe';
import localePt from '@angular/common/locales/pt';
import { Page } from '../../../appointments/appointment-model';

declare var bootstrap: any;
registerLocaleData(localePt);

@Component({
  selector: 'app-product-list',
  imports: [CommonModule, FormsModule, DurationPipe],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})

export class ProductListComponent implements OnInit {

  products: ProductResponse[] = [];
  product: ProductRequest = { name: '', durationInSeconds: 0, price: 0 };

  page?: Page<ProductResponse>;
  currentPage = 0
  pageSize = 10

  searchName = '';
  successMessage = '';
  errorMessage = '';
  successToast: any;
  errorToast: any;

  selectedProductId: string | null = null;
  editingId: string | null = null;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllPaged(this.currentPage, this.pageSize)
      .subscribe((data: any) => {
        this.page = data
        this.products = data.content
      });
  }

  search() {
    if (!this.searchName) {
      this.loadProducts();
      return;
    }
    this.productService.getByName(this.searchName)
      .subscribe((data: any) => {
        this.products = data;
      });
  }

  save(form: any) {
    if (this.product.price == 0) {
      this.showError('Valor do produto precisa ser maior que Zero');
      return;
    }

    if (this.product.durationInSeconds == 0) {
      this.showError('Valor de tempo inválido');
      return;
    }

    //durationsInSeconts (min -> sec)
    this.product.durationInSeconds = this.product.durationInSeconds * 60;

    if (this.editingId) {
      this.productService.update(this.editingId, this.product)
        .subscribe({
          next: () => {
            this.showSuccess('Produto alterado com sucesso!');
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao alterar o produto. Tente novamente.');
          }
        });

    } else {
      this.productService.create(this.product)
        .subscribe({
          next: () => {
            this.showSuccess('Produto salvo com sucesso!');
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao salvar o produto. Tente novamente.');
          }
        });
    }
  }

  afterSave(form: any) {
    this.loadProducts();
    form.reset();

    this.product = { name: '', durationInSeconds: 0, price: 0 };
    this.editingId = null;

    const modal = bootstrap.Modal.getInstance(
      document.getElementById('productModal')
    );
    modal.hide();
  }

  change(id: string) {
    this.productService.getById(id)
      .subscribe((data) => {

        this.product = {
          name: data.name,
          durationInSeconds: data.durationInSeconds,
          price: data.price
        };

        // Convert to presentation
        this.product.durationInSeconds = this.product.durationInSeconds / 60;

        this.editingId = id;

        const modal = new bootstrap.Modal(
          document.getElementById('productModal')
        );

        modal.show();

      });
  }

  delete() {
    if (!this.selectedProductId) return;

    this.productService.delete(this.selectedProductId)
      .subscribe(() => {

        this.loadProducts();

        const modal = bootstrap.Modal.getInstance(
          document.getElementById('deleteModal')
        );

        modal.hide();

      });
  }

  openDeleteModal(id: string) {
    this.selectedProductId = id;

    const modal = new bootstrap.Modal(
      document.getElementById('deleteModal')
    );

    modal.show();
  }

  ngAfterViewInit() {
    const successEl = document.getElementById('successToast');
    const errorEl = document.getElementById('errorToast');

    if (successEl) {
      this.successToast = new bootstrap.Toast(successEl, { delay: 6000 });
    }

    if (errorEl) {
      this.errorToast = new bootstrap.Toast(errorEl, { delay: 4000 });
    }
  }

  showSuccess(message: string) {
    this.successMessage = message;
    if (this.successToast) {
      this.successToast.show();
    }
  }

  showError(message: string) {
    this.errorMessage = message;
    this.errorToast.show();
  }

  nextPage() {
    if (!this.page?.last) {
      this.currentPage++
      this.loadProducts()
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--
      this.loadProducts()
    }
  }

}