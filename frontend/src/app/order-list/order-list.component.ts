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

  getCardTypePath(cardNumber: string): string {
    const path = "assets/images/card_brand/";
    const cleanedNumber = cardNumber.replace(/\D/g, ''); // Remove qualquer caractere não numérico

    // Verifique se o número do cartão tem comprimento válido
    if (cleanedNumber.length < 13 || cleanedNumber.length > 19) {
      return path + 'credit-card.png'; // Retorna imagem padrão se o comprimento for inválido
    }

    if (cleanedNumber.startsWith('4')) {
      return path + 'visa.svg';
    } else if (/^5[1-5]/.test(cleanedNumber)) {
      return path + 'mastercard.svg';
    } else if (/^3[47]/.test(cleanedNumber)) {
      return path + 'american-express.svg';
    } else if (/^3(6|8|9)/.test(cleanedNumber)) {
      return path + 'diners-club-international.svg';
    } else if (/^(6011|65|64[4-9]|622(12[6-9]|1[3-9]\d|[2-8]\d{2}|9[01]\d|92[0-5]))/.test(cleanedNumber)) {
      return path + 'discover-card.svg';
    } else {
      return path + 'credit-card.png'; // Imagem padrão para cartões não identificados
    }
  }
}
