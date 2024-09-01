import { Routes } from '@angular/router';

import { OrderRegisterComponent } from './order-register/order-register.component';
import { OrderListComponent } from './order-list/order-list.component';


export const routes: Routes = [
    { path: '', redirectTo: '/orders', pathMatch: 'full' },
    { path: 'orders', component: OrderListComponent },
    { path: 'register', component: OrderRegisterComponent }
];
