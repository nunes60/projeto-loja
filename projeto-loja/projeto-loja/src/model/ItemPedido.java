package model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoUnitario; // Histórico do preço no momento da compra

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    // Calcula o subtotal deste item
    public double getPrecoTotal() {
        return this.quantidade * this.precoUnitario;
    }
}