import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderService } from '../services/order.service'; // Importa o OrderService
import { Order } from '../models/order.model'; // Importa o modelo Order
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { NgxCurrencyDirective } from 'ngx-currency';
import { Router } from '@angular/router';
import { CryptoUtils } from '../../utils/crypto.util';
import { environment } from '../../environments/environment';

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



  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router, private cryptoUtils: CryptoUtils) { // Injetar OrderService
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

  onCancel(): void {
    this.router.navigate(['/']);
  }

  async onSubmit() {


    if (this.orderForm.valid) {
      try {
        let order = {

          "description": this.orderForm.value.description,
          "amount": this.orderForm.value.amount,
          "status": "pendent",
          "creditCard": {
            "cardNumber": this.cryptoUtils.encrypt(this.orderForm.value.creditCard.cardNumber, environment.secretKey),
            "holder": this.cryptoUtils.encrypt(this.orderForm.value.creditCard.holder, environment.secretKey),
            "expirationDate": this.cryptoUtils.encrypt(this.orderForm.value.creditCard.expirationDate, environment.secretKey),
            "securityCode": this.cryptoUtils.encrypt(this.orderForm.value.creditCard.securityCode, environment.secretKey),
            "brand": this.cryptoUtils.encrypt(this.orderForm.value.creditCard.brand, environment.secretKey)
          }

        }


        this.isLoading = true;
        const orderResponse = await this.orderService.registerOrder(order);



        console.log(orderResponse);
        if (orderResponse.id != null) {

          this.router.navigate(['/orders']);
        }

      } catch (error) {

      } finally {
        this.isLoading = false;
      }
    }
  }
}
