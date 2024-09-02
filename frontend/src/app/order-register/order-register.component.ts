import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderService } from '../services/order.service'; // Importa o OrderService
import { Order } from '../models/order.model'; // Importa o modelo Order
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { NgxCurrencyDirective } from 'ngx-currency';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, NgxMaskDirective, NgxMaskPipe, FormsModule, NgxCurrencyDirective],
  templateUrl: './order-register.component.html',
  styleUrls: ['./order-register.component.scss']
})
export class OrderRegisterComponent {
  orderForm: FormGroup;
  isLoading: boolean = false;



  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router) { // Injetar OrderService
    this.orderForm = this.fb.group({
      description: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0)]],
      creditCard: this.fb.group({
        cardNumber: ['', Validators.required],
        holder: ['', Validators.required],
        expirationDate: ['', Validators.required],
        securityCode: ['', Validators.required],
        brand: ['', Validators.required]
      })
    });
  }



  async onSubmit() {


    if (this.orderForm.valid) {
      const order: Order = this.orderForm.value;

      try {
        this.isLoading = true;
        const order = await this.orderService.registerOrder(this.order);



        console.log(order);
        if (order.id != null) {

          this.router.navigate(['/orders']);
        }

      } catch (error) {

      } finally {
        this.isLoading = false;
      }
    }
  }
}
