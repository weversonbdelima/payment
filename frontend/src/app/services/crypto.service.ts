import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
    providedIn: 'root'
})
export class CryptoService {
    private readonly ALGORITHM = 'AES';

    encrypt(data: string, secretKey: string): string {
        // Converte a chave para UTF-8
        const key = CryptoJS.enc.Utf8.parse(secretKey);

        // Criptografa os dados usando o modo ECB
        const encrypted = CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(data), key, {
            mode: CryptoJS.mode.ECB, // Mude para ECB
            padding: CryptoJS.pad.Pkcs7 // Usando padding PKCS7
        });

        // Retorna os dados criptografados em Base64
        return encrypted.toString();
    }
}
