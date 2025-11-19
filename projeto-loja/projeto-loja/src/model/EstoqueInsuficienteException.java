package model;

// Exceção verificada (Checked Exception) para obrigar o tratamento
public class EstoqueInsuficienteException extends Exception {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}