
# Projeto Loja em Java (Faculdade SENAC)

Este é um sistema de gerenciamento de loja desenvolvido em Java como parte das atividades acadêmicas da Faculdade SENAC. O projeto implementa um controle de vendas com validação de estoque, cadastro de clientes e produtos, utilizando conceitos de Orientação a Objetos.

## 📋 Funcionalidades

O sistema atende aos seguintes requisitos funcionais:

* **Gestão de Produtos:** Cadastro de produtos com nome, preço e quantidade em estoque.
* **Gestão de Clientes:** Cadastro de clientes com nome, e-mail e endereço.
* **Processamento de Pedidos:**
    * Criação de pedidos associados a um cliente.
    * Adição de itens ao carrinho.
    * **Validação de Estoque:** O sistema impede a venda se a quantidade solicitada for maior que a disponível (lança `EstoqueInsuficienteException`).
    * Cálculo automático do valor total.
* **Interfaces:**
    * Modo Console (CLI) para testes rápidos.
    * Interface Gráfica (GUI) feita com **Java Swing**.

## 🚀 Tecnologias Utilizadas

* Java (JDK)
* Java Swing (Interface Gráfica)
* Estrutura MVC (Model - View - Service)

## 📂 Estrutura do Projeto

* `src/model`: Classes de dados (`Produto`, `Cliente`, `Pedido`, `ItemPedido`) e Exceções.
* `src/service`: Lógica de negócios (`Loja`), responsável por armazenar os dados em memória (Mapas).
* `src/gui`: Interface gráfica do usuário (`LojaUI`, `MainGUI`).
* `src/Main.java`: Classe principal para execução via Terminal (Cenários de teste).

---
**Autor**
* João Paulo Nunes da Silva - Faculdade SENAC DF
