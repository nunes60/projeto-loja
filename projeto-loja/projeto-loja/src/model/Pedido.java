package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private String status; // "ABERTO", "PROCESSADO", "CANCELADO"
    private LocalDateTime dataHora;
    private List<ItemPedido> itens;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.status = "ABERTO";
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>();
    }

    // RF-004: Adicionar itens ao pedido
    public void adicionarItem(Produto p, int qtd) {
        this.itens.add(new ItemPedido(p, qtd));
    }

    // RF-010: Calcular total somando subtotais
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getPrecoTotal();
        }
        return total;
    }

    public List<ItemPedido> getItens() { return itens; }
    public int getId() { return id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Cliente getCliente() { return cliente; }

    @Override
    public String toString() {
        return String.format("Pedido #%d | Cliente: %s | Status: %s | Total: R$ %.2f", 
            id, cliente.getName(), status, calcularTotal());
    }
}