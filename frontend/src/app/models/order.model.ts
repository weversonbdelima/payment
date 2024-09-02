import { CreditCard } from "./creditCard.model";

export interface Order {
    id: number;
    description: string;
    amount: number;
    status: string;
    creditCard: CreditCard;
}