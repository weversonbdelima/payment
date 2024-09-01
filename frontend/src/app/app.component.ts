import { Component } from '@angular/core';
import { RouterOutlet, Routes, RouterModule } from '@angular/router';
import { OrderRegisterComponent } from './order-register/order-register.component';
import { OrderListComponent } from './order-list/order-list.component';
import { NavComponent } from './components/nav/nav.component';




@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,

    NavComponent,

    OrderListComponent,
    OrderRegisterComponent,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
}
