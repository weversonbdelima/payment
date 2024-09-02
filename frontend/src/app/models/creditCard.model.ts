export class CreditCard {
    cardNumber: string;
    holder: string;
    expirationDate: string;
    securityCode: string;
    brand: string;

    constructor(
        cardNumber: string,
        holder: string,
        expirationDate: string,
        securityCode: string,
        brand: string
    ) {
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.brand = brand;
    }
}
