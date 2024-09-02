import { CreditCard } from "./creditCard.model";
export class Order {
    id?: number;
    description?: string;
    amount?: number;
    status?: string;
    creditCard?: CreditCard;

    constructor(

        description?: string,
        amount?: number,
        status?: string,
        creditCard?: CreditCard
    ) {

        this.description = description;
        this.amount = amount;
        this.status = status;
        this.creditCard = creditCard;
    }
}