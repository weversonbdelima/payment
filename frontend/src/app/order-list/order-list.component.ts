import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../services/order.service'; // Ajuste o caminho conforme necessário
import { Order } from '../models/order.model'; // Ajuste o caminho conforme necessário



@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule,], // Adiciona o HttpClientModule aqui
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.fetchOrders();
  }

  async fetchOrders() {
    console.log('Carregando pedidos...');
    this.orders = await this.orderService.getOrders();


  }

  cancelOrder(id: number): void {


  }
  maskAndFormatCardNumber(cardNumber: string): string {
    if (!cardNumber) return '';

    const cleaned = cardNumber.replace(/\D/g, '');
    const lastFourDigits = cleaned.slice(-4);
    const maskedPart = '*'.repeat(cleaned.length - 4);
    
    const formattedMasked = maskedPart + lastFourDigits; // Máscara com os últimos 4 dígitos
    const formatted = formattedMasked.replace(/(.{4})/g, '$1 ').trim(); // Formatação com espaço

    return formatted;
}
}
