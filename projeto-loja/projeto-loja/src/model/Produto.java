package model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    // RF-007: Atualização de estoque
    public void diminuirEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (quantidade > this.estoque) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + this.nome);
        }
        this.estoque -= quantidade;
    }

    public void aumentarEstoque(int quantidade) {
        this.estoque += quantidade;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | R$ %.2f | Qtd: %d", id, nome, preco, estoque);
    }
}