package service;

import model.*;
import java.util.HashMap;
import java.util.Map;

public class Loja {
    private String nome;
    // Armazenamento em memória (HashMaps) conforme RF-001, RF-002 [cite: 77]
    private Map<Integer, Produto> produtos;
    private Map<Integer, Cliente> clientes;
    private Map<Integer, Pedido> pedidos;

    // Contadores para IDs automáticos (simulação de Auto Increment)
    private int nextProdutoId = 1;
    private int nextClienteId = 1;
    private int nextPedidoId = 1;

    public Loja(String nome) {
        this.nome = nome;
        this.produtos = new HashMap<>();
        this.clientes = new HashMap<>();
        this.pedidos = new HashMap<>();
    }

    // RF-001: Cadastrar Produto
    public void adicionarProduto(String nome, double preco, int estoqueInicial) {
        Produto p = new Produto(nextProdutoId++, nome, preco, estoqueInicial);
        produtos.put(p.getId(), p);
        System.out.println("Produto cadastrado: " + p);
    }

    // RF-002: Cadastrar Cliente
    public void cadastrarCliente(String nome, String email, String endereco) {
        Cliente c = new Cliente(nextClienteId++, nome, email, endereco);
        clientes.put(c.getId(), c);
        System.out.println("Cliente cadastrado: " + c);
    }

    public Produto buscarProduto(int id) {
        return produtos.get(id);
    }

    public Cliente buscarCliente(int id) {
        return clientes.get(id);
    }

    // Expose read-only views for GUI and reporting
    public java.util.Collection<Produto> getProdutos() { return produtos.values(); }
    public java.util.Collection<Cliente> getClientes() { return clientes.values(); }
    public java.util.Collection<Pedido> getPedidos() { return pedidos.values(); }

    // RF-003: Criar Pedido
    public Pedido criarPedido(int clienteId) {
        // Corrige referência da variável 'clientes'
        Cliente c = clientes.get(clienteId);
        if (c == null) {
            System.out.println("Cliente não encontrado!");
            return null;
        }
        Pedido p = new Pedido(nextPedidoId++, c);
        pedidos.put(p.getId(), p);
        return p;
    }

    // RF-005, RF-006, RF-007, RF-008: Processar Pedido com validação
    public void processarPedido(int pedidoId) {
        Pedido p = pedidos.get(pedidoId);
        if (p == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        if (!p.getStatus().equals("ABERTO")) {
            System.out.println("Pedido já foi processado ou cancelado.");
            return;
        }

        try {
            System.out.println("\n--- Processando Pedido #" + p.getId() + " ---");
            
            // 1. Validação prévia: Verifica se há estoque para TODOS os itens antes de debitar
            for (ItemPedido item : p.getItens()) {
                Produto produtoNoEstoque = item.getProduto();
                // Verifica se a quantidade solicitada é maior que o estoque atual
                if (item.getQuantidade() > produtoNoEstoque.getEstoque()) {
                    throw new EstoqueInsuficienteException("Estoque insuficiente para: " + produtoNoEstoque.getNome());
                }
            }

            // 2. Efetivação: Se chegou aqui, tem estoque para tudo. Debita.
            for (ItemPedido item : p.getItens()) {
                item.getProduto().diminuirEstoque(item.getQuantidade());
            }

            // Sucesso: Marca como PROCESSADO
            p.setStatus("PROCESSADO");
            System.out.println("Sucesso! Pedido processado. Valor Total: R$ " + p.calcularTotal());

        } catch (EstoqueInsuficienteException e) {
            // RF-008: Se estoque insuficiente, marca como CANCELADO
            p.setStatus("CANCELADO");
            System.err.println("ERRO: " + e.getMessage());
            System.out.println("O pedido foi CANCELADO automaticamente.");
        }
    }

    // RF-011: Relatório de Estoque
    public void listarProdutos() {
        System.out.println("\n--- Catálogo de Produtos ---");
        for (Produto p : produtos.values()) {
            System.out.println(p);
        }
    }
    
    // RF-012: Relatório de Pedidos
    public void listarPedidos() {
        System.out.println("\n--- Histórico de Pedidos ---");
        for (Pedido p : pedidos.values()) {
            System.out.println(p);
        }
    }
}