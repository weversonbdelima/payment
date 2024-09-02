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

  async cancelOrder(id: number) {
    try {
      const order = await this.orderService.cancelOrder(id);

      // Check if the order was successfully canceled
      if (order && order.status === "canceled") {
        alert('Pedido cancelado!')

        await this.fetchOrders();
        console.log("Order canceled successfully:", order);
        // Additional actions can be performed here, such as notifying the user or updating the UI
      } else {
        console.log("Failed to cancel order. Current status:", order ? order.status : "No order found");
        // Handle case where the order was not canceled
      }
    } catch (error: any) {

      const errorMessage = error?.message || "Erro desconhecido. Tente novamente.";
      alert(errorMessage); // Show the error message in an alert
      console.error("Error while canceling the order:", error);
    }
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
