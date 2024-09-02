import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../services/order.service'; 
import { Order } from '../models/order.model';



@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule,], 
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
    this.orders = await this.orderService.getOrders();
  }

  async cancelOrder(id: number) {
    try {
      const order = await this.orderService.cancelOrder(id);


      if (order && order.status === "canceled") {
        alert('Pedido cancelado!')

        await this.fetchOrders();
        console.log("Order canceled successfully:", order);
   
      } else {
        console.log("Failed to cancel order. Current status:", order ? order.status : "No order found");
     
      }
    } catch (error: any) {

      const errorMessage = error?.message || "Erro desconhecido. Tente novamente.";
      alert(errorMessage); 
      console.error("Error while canceling the order:", error);
    }
  }
  maskAndFormatCardNumber(cardNumber: string): string {
    if (!cardNumber) return '';

    const cleaned = cardNumber.replace(/\D/g, '');
    const lastFourDigits = cleaned.slice(-4);
    const maskedPart = '*'.repeat(cleaned.length - 4);

    const formattedMasked = maskedPart + lastFourDigits; 
    const formatted = formattedMasked.replace(/(.{4})/g, '$1 ').trim(); 

    return formatted;
  }

  getCardTypePath(cardNumber: string): string {
    const path = "assets/images/card_brand/";
    const cleanedNumber = cardNumber.replace(/\D/g, ''); 


    if (cleanedNumber.length < 13 || cleanedNumber.length > 19) {
      return path + 'credit-card.png'; 
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
      return path + 'credit-card.png'; 
    }
  }
}
