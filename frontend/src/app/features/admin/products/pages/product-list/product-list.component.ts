import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, registerLocaleData } from '@angular/common';
import { ProductRequest, ProductResponse } from '../../product-modal';
import { ProductService } from '../../product.service';
import { DurationPipe } from '../../../../../core/pipes/duration.pipe';
import localePt from '@angular/common/locales/pt';

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

  searchName = '';
  toastMessage = '';

  selectedProductId: string | null = null;
  editingId: string | null = null;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {

    this.productService.getAll()
      .subscribe((data: any) => {
        const list = this.products = data as ProductResponse[];
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
    if (this.editingId) {
      this.productService.update(this.editingId, this.product)
        .subscribe(() => {

          this.toastMessage = "Product updated successfully";

          this.afterSave(form);

        });

    } else {
      this.productService.create(this.product)
        .subscribe(() => {

          this.toastMessage = "Product created successfully";

          this.afterSave(form);

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

    this.showToast();
  }

  change(id: string) {
    this.productService.getById(id)
      .subscribe((data) => {

        this.product = {
          name: data.name,
          durationInSeconds: data.durationInSeconds,
          price: data.price
        };

        this.editingId = id;

        const modal = new bootstrap.Modal(
          document.getElementById('productModal')
        );

        modal.show();

      });
  }

  showToast() {
    const toastEl = document.getElementById('successToast');
    const toast = new bootstrap.Toast(toastEl);

    toast.show();
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

}