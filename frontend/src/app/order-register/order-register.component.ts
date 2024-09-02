import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderService } from '../services/order.service'; // Importa o OrderService
import { Order } from '../models/order.model'; // Importa o modelo Order
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { NgxCurrencyDirective } from 'ngx-currency';
import { Router } from '@angular/router';
import * as CryptoJS from 'crypto-js';
import { environment } from '../../environments/environment';
import { CryptoService } from '../services/crypto.service';
import { CreditCard } from '../models/creditCard.model';

@Component({
  selector: 'app-order-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, NgxMaskDirective, NgxMaskPipe, FormsModule, NgxCurrencyDirective],
  templateUrl: './order-register.component.html',
  styleUrls: ['./order-register.component.scss']
})
export class OrderRegisterComponent implements OnInit {
  orderForm: FormGroup;
  isLoading: boolean = false;

  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router, private cryptoService: CryptoService) {
    this.orderForm = this.fb.group({
      description: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0)]],
      creditCard: this.fb.group({
        cardNumber: ['', Validators.required],
        holder: ['', Validators.required],
        expirationDate: ['', Validators.required],
        securityCode: ['', Validators.required],
        brand: ['', Validators.required] // Adiciona o controle para a marca do cartão
      })
    });
  }

  ngOnInit() { }

  encryptData(data: string, secretKey: string): string {
    console.log(secretKey);
    const encryptedData = CryptoJS.AES.encrypt(data, secretKey).toString();
    return encryptedData;
  }

  encryptCard(card: CreditCard) {
    const encryptedCard = new CreditCard(
      this.cryptoService.encrypt(card.cardNumber, environment.secretKey),
      this.cryptoService.encrypt(card.holder, environment.secretKey),
      this.cryptoService.encrypt(card.expirationDate, environment.secretKey),
      this.cryptoService.encrypt(card.securityCode, environment.secretKey),
      this.cryptoService.encrypt(card.brand, environment.secretKey) // Adiciona a marca do cartão
    );

    return encryptedCard;
  }

  onCancel(): void {
    this.router.navigate(['/']);
  }

  async onSubmit() {
    if (this.orderForm.valid) {
      let order: Order = new Order();

      order.description = this.orderForm.value.description;
      order.amount = this.orderForm.value.amount;
      order.status = 'pendent'; // ou outro status padrão

      order.creditCard = {
        securityCode: this.orderForm.value.creditCard.securityCode,
        cardNumber: this.orderForm.value.creditCard.cardNumber,
        holder: this.orderForm.value.creditCard.holder,
        expirationDate: this.orderForm.value.creditCard.expirationDate,
        brand: this.orderForm.value.creditCard.brand // Adiciona a marca do cartão
      };

      order.creditCard = this.encryptCard(order.creditCard);

      try {
        this.isLoading = true;

        const orderResponse = await this.orderService.registerOrder(order);

        console.log(order);
        if (orderResponse.id != null) {
          this.router.navigate(['/orders']);
        }
      } catch (error) {
        console.error('Erro ao registrar o pedido:', error);
      } finally {
        this.isLoading = false;
      }
    }
  }
}
