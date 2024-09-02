# **TransactX**

![Badge de status do projeto](https://img.shields.io/badge/status-em%20desenvolvimento-yellow) ![Badge de licença](https://img.shields.io/badge/license-MIT-blue)

**Descrição**: 

Esta aplicação foi desenvolvida para permitir o registro de compras fictícias e a realização do processo de pagamento utilizando a API de Sandbox da Cielo. O projeto inclui a implementação tanto do front-end quanto do back-end, garantindo uma comunicação eficiente e segura entre as duas camadas, bem como uma integração robusta com a API da Cielo.

O front-end foi criado para oferecer uma interface amigável e intuitiva, onde os usuários podem inserir os detalhes da compra e acompanhar o status das transações. Já o back-end é responsável por processar os dados, comunicar-se com a API da Cielo para efetuar os pagamentos e retornar os resultados ao front-end.

## **Sumário**

- [Introdução](#introdução)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Como Usar](#como-usar)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Contato](#contato)

## **Introdução**

Este projeto foi concebido como uma solução completa para simular o processo de registro e pagamento de compras fictícias, utilizando a API de Sandbox da Cielo. Com a crescente demanda por sistemas de pagamento integrados, esta aplicação serve como um exemplo prático de como implementar um fluxo de pagamento end-to-end, desde a interface de usuário até a comunicação segura com uma API de terceiros. Ao integrar front-end e back-end de forma coesa, o projeto oferece uma plataforma para aprender e explorar as melhores práticas em desenvolvimento full-stack, especialmente no contexto de transações financeiras digitais.

## **Funcionalidades**

- **Registro de Vendas**: Permite ao usuário registrar dados de uma compra fictícia, incluindo descrição, valor e informações do cartão de crédito.
- **Processamento de Pagamento**: Integração com a API de Sandbox da Cielo para processar o pagamento das vendas registradas, com captura automática.
- **Exibição do Status da Transação**: Mostra o status da transação (sucesso ou falha) após o processamento do pagamento.
- **Tratamento de Erros e Validações**: Implementação de validações nos formulários do front-end e tratamento adequado de erros para garantir a consistência e segurança dos dados inseridos.
- **Listagem de Vendas**: Exibe uma lista completa de todas as vendas realizadas, permitindo ao usuário visualizar o histórico de transações.
- **Cancelamento de Vendas**: Funcionalidade adicional que permite o cancelamento de uma venda e do pagamento correspondente junto à Cielo.
- **Criptografia de Dados Sensíveis**: Implementação de criptografia para proteger os dados sensíveis, como informações do cartão de crédito, garantindo a segurança durante o processamento.

## **Tecnologias Utilizadas**

- **Frontend**: Angular, HTML5, CSS3, Bootstrap
- **Backend**: Spring Boot, Java
- **Banco de Dados**: PostgreSQL
- **Outras Tecnologias**: Docker, Docker Compose, Cielo API (Sandbox)

## **Instalação**

Siga os passos abaixo para configurar o ambiente e rodar a aplicação localmente:

1. Clone o repositório do projeto:
    ```bash
    git clone https://github.com/weversonbdelima/payment.git
    ```

2. Entre no diretório do projeto:
    ```bash
    cd payment
    ```

3. Inicie o banco de dados utilizando o Docker Compose:
    ```bash
    sudo docker-compose up
    ```

Após seguir esses passos, o banco de dados estará configurado e pronto para uso. Em seguida, você poderá proceder com a instalação e execução do front-end e back-end da aplicação.