import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig, AxiosError } from 'axios';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class CustomHttpClient {

    private headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + environment.token
    };

    constructor() { }

    private handleError(error: AxiosError) {

        return Promise.reject(error.message || 'Erro desconhecido');
    }

    async get(url: string) {
        const config: AxiosRequestConfig = {
            headers: this.headers,
        };

        try {
            const response = await axios.get(url, config);
            return response.data;
        } catch (error) {
            return this.handleError(error as AxiosError);
        }
    }

    async post(url: string, body?: any) {
        const config: AxiosRequestConfig = {
            headers: this.headers,
        };

        try {
            const response = await axios.post(url, body, config);
            return response.data;
        } catch (error) {
            return this.handleError(error as AxiosError);
        }
    }
}
