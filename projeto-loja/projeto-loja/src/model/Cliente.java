package model;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String endereco;

    public Cliente(int id, String nome, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return nome; } // Conforme diagrama [cite: 21]
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + email + ")";
    }
}