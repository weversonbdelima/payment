import { Injectable } from '@angular/core';
import { CustomHttpClient } from './custom-http-client.service';

import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = environment.apiUrl + '/orders';

  constructor(private customHttpClient: CustomHttpClient) { }

  async registerOrder(order: any) {
    return await this.customHttpClient.post(this.apiUrl, order,);
  }

  async getOrders() {
    return await this.customHttpClient.get(this.apiUrl);
  }
}
