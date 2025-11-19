import service.Loja;
import model.Pedido;
import model.Produto;
import model.Cliente;

public class Main {
    public static void main(String[] args) {
        // Inicializa a Loja
        Loja minhaLoja = new Loja("TechStore Java");

        System.out.println("=== INICIALIZANDO SISTEMA ===");

        // 1. Cadastro de Produtos (RF-001)
        minhaLoja.adicionarProduto("Notebook Dell", 3500.00, 5); // ID 1
        minhaLoja.adicionarProduto("Mouse Logitech", 120.00, 10); // ID 2
        minhaLoja.adicionarProduto("Teclado Mecânico", 250.00, 2); // ID 3

        // 2. Cadastro de Clientes (RF-002)
        minhaLoja.cadastrarCliente("João Silva", "joao@email.com", "Rua A, 123"); // ID 1
        minhaLoja.cadastrarCliente("Maria Oliveira", "maria@email.com", "Rua B, 456"); // ID 2

        // ---------------------------------------------------------
        // CENÁRIO 1: Compra com Sucesso (Happy Path)
        // ---------------------------------------------------------
        System.out.println("\n=== CENÁRIO 1: Compra Válida ===");
        Pedido pedido1 = minhaLoja.criarPedido(1); // Cliente João
        
        Produto p1 = minhaLoja.buscarProduto(1); // Notebook
        Produto p2 = minhaLoja.buscarProduto(2); // Mouse

        // Adiciona itens (RF-004)
        pedido1.adicionarItem(p1, 1); // 1 Notebook (Estoque vai para 4)
        pedido1.adicionarItem(p2, 2); // 2 Mouses (Estoque vai para 8)

        // Processa (RF-005, RF-006, RF-007)
        minhaLoja.processarPedido(pedido1.getId());

        // ---------------------------------------------------------
        // CENÁRIO 2: Compra com Estoque Insuficiente (Erro)
        // ---------------------------------------------------------
        System.out.println("\n=== CENÁRIO 2: Estoque Insuficiente ===");
        Pedido pedido2 = minhaLoja.criarPedido(2); // Cliente Maria
        
        Produto p3 = minhaLoja.buscarProduto(3); // Teclado (Estoque atual: 2)

        // Tenta comprar 3 teclados (só tem 2 no estoque)
        pedido2.adicionarItem(p3, 3); 

        // Deve falhar, lançar exceção e cancelar o pedido (RF-008, RF-009)
        minhaLoja.processarPedido(pedido2.getId());

        // ---------------------------------------------------------
        // RELATÓRIOS FINAIS
        // ---------------------------------------------------------
        minhaLoja.listarProdutos(); // Verifica se estoque baixou corretamente
        minhaLoja.listarPedidos();  // Verifica status PROCESSADO vs CANCELADO
    }
}