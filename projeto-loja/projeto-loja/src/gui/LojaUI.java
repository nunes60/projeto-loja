package gui;

import service.Loja;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
// imports
import java.util.ArrayList;
import java.util.List;

public class LojaUI extends JFrame {
    private Loja loja;

    private DefaultTableModel produtosTableModel;
    private DefaultTableModel clientesTableModel;
    private DefaultTableModel itensPedidoModel;

    private JComboBox<String> comboClientes;
    private JComboBox<String> comboProdutos;
    private JTextField qtdField;

    private List<ItemPedido> itensDoPedido;
    private Pedido pedidoAtual;

    public LojaUI() {
        super("Loja - Interface Swing");
        this.loja = new Loja("TechStore Swing");
        this.itensDoPedido = new ArrayList<>();

        // Pre-popula a loja (mesmo cenário do Main)
        inicializarDados();

        initComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
    }

    private void inicializarDados() {
        loja.adicionarProduto("Notebook Dell", 3500.00, 5);
        loja.adicionarProduto("Mouse Logitech", 120.00, 10);
        loja.adicionarProduto("Teclado Mecânico", 250.00, 2);

        loja.cadastrarCliente("João Silva", "joao@email.com", "Rua A, 123");
        loja.cadastrarCliente("Maria Oliveira", "maria@email.com", "Rua B, 456");
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Produtos Tab
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosTableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Estoque"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable produtosTable = new JTable(produtosTableModel);
        produtosPanel.add(new JScrollPane(produtosTable), BorderLayout.CENTER);

        JPanel produtoForm = new JPanel(new FlowLayout());
        JTextField nomeProd = new JTextField(15);
        JTextField precoProd = new JTextField(7);
        JTextField estoqueProd = new JTextField(5);
        JButton btnAdicionarProd = new JButton("Adicionar Produto");
        produtoForm.add(new JLabel("Nome")); produtoForm.add(nomeProd);
        produtoForm.add(new JLabel("Preço")); produtoForm.add(precoProd);
        produtoForm.add(new JLabel("Estoque")); produtoForm.add(estoqueProd);
        produtoForm.add(btnAdicionarProd);
        produtosPanel.add(produtoForm, BorderLayout.SOUTH);

        // Clientes Tab
        JPanel clientesPanel = new JPanel(new BorderLayout());
        clientesTableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable clientesTable = new JTable(clientesTableModel);
        clientesPanel.add(new JScrollPane(clientesTable), BorderLayout.CENTER);

        JPanel clienteForm = new JPanel(new FlowLayout());
        JTextField nomeCli = new JTextField(15);
        JTextField emailCli = new JTextField(15);
        JTextField enderecoCli = new JTextField(10);
        JButton btnAdicionarCli = new JButton("Adicionar Cliente");
        clienteForm.add(new JLabel("Nome")); clienteForm.add(nomeCli);
        clienteForm.add(new JLabel("Email")); clienteForm.add(emailCli);
        clienteForm.add(new JLabel("Endereço")); clienteForm.add(enderecoCli);
        clienteForm.add(btnAdicionarCli);
        clientesPanel.add(clienteForm, BorderLayout.SOUTH);

        // Pedidos Tab
        JPanel pedidosPanel = new JPanel(new BorderLayout());

        JPanel pedidoTopo = new JPanel(new FlowLayout());
        comboClientes = new JComboBox<>();
        comboProdutos = new JComboBox<>();
        qtdField = new JTextField(4);
        JButton btnNovoPedido = new JButton("Novo Pedido");
        JButton btnAdicionarItem = new JButton("Adicionar Item");
        JButton btnProcessar = new JButton("Processar Pedido");

        pedidoTopo.add(new JLabel("Cliente")); pedidoTopo.add(comboClientes);
        pedidoTopo.add(new JLabel("Produto")); pedidoTopo.add(comboProdutos);
        pedidoTopo.add(new JLabel("Qtd")); pedidoTopo.add(qtdField);
        pedidoTopo.add(btnNovoPedido); pedidoTopo.add(btnAdicionarItem); pedidoTopo.add(btnProcessar);
        pedidosPanel.add(pedidoTopo, BorderLayout.NORTH);

        itensPedidoModel = new DefaultTableModel(new Object[]{"Produto", "Qtd", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable itensTable = new JTable(itensPedidoModel);
        pedidosPanel.add(new JScrollPane(itensTable), BorderLayout.CENTER);

        // Log area
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        pedidosPanel.add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // Adiciona tabs
        tabbedPane.addTab("Produtos", produtosPanel);
        tabbedPane.addTab("Clientes", clientesPanel);
        tabbedPane.addTab("Pedidos", pedidosPanel);

        this.add(tabbedPane);

        // Ações:
        btnAdicionarProd.addActionListener(e -> {
            try {
                String nome = nomeProd.getText().trim();
                double preco = Double.parseDouble(precoProd.getText().trim());
                int estoque = Integer.parseInt(estoqueProd.getText().trim());
                loja.adicionarProduto(nome, preco, estoque);
                refreshProdutos();
                refreshProdutosCombo();
                nomeProd.setText(""); precoProd.setText(""); estoqueProd.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage());
            }
        });

        btnAdicionarCli.addActionListener(e -> {
            String nome = nomeCli.getText().trim();
            String email = emailCli.getText().trim();
            String endereco = enderecoCli.getText().trim();
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Email são obrigatórios");
                return;
            }
            loja.cadastrarCliente(nome, email, endereco);
            refreshClientes();
            refreshClientesCombo();
            nomeCli.setText(""); emailCli.setText(""); enderecoCli.setText("");
        });

        btnNovoPedido.addActionListener(e -> {
            String selected = (String) comboClientes.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente antes de criar o pedido.");
                return;
            }
            int clienteId = Integer.parseInt(selected.split(" - ")[0].trim());
            pedidoAtual = loja.criarPedido(clienteId);
            if (pedidoAtual != null) {
                itensPedidoModel.setRowCount(0);
                itensDoPedido.clear();
                logArea.append("Novo pedido criado: #" + pedidoAtual.getId() + "\n");
            } else {
                logArea.append("Falha ao criar pedido: cliente não encontrado\n");
            }
        });

        btnAdicionarItem.addActionListener(e -> {
            if (pedidoAtual == null) {
                JOptionPane.showMessageDialog(this, "Crie um pedido primeiro.");
                return;
            }
            String selectedP = (String) comboProdutos.getSelectedItem();
            if (selectedP == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto.");
                return;
            }
            int produtoId = Integer.parseInt(selectedP.split(" - ")[0].trim());
            Produto p = loja.buscarProduto(produtoId);
            int qtd;
            try { qtd = Integer.parseInt(qtdField.getText().trim()); } catch (Exception ex) { qtd = 0; }
            if (p == null || qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Produto inválido ou quantidade inválida");
                return;
            }
            pedidoAtual.adicionarItem(p, qtd);
            itensPedidoModel.addRow(new Object[]{p.getNome(), qtd, String.format("R$ %.2f", p.getPreco() * qtd)});
            logArea.append("Item adicionado: " + p.getNome() + " x" + qtd + "\n");
            qtdField.setText("");
        });

        btnProcessar.addActionListener(e -> {
            if (pedidoAtual == null) {
                JOptionPane.showMessageDialog(this, "Crie um pedido antes de processar.");
                return;
            }
            loja.processarPedido(pedidoAtual.getId());
            logArea.append("Pedido processado: #" + pedidoAtual.getId() + " | Status: " + pedidoAtual.getStatus() + "\n");
            refreshProdutos();
            // limpa pedido atual
            pedidoAtual = null;
        });

        // Inicializa tabelas e combos
        refreshProdutos();
        refreshClientes();
        refreshProdutosCombo();
        refreshClientesCombo();
    }

    private void refreshProdutos() {
        produtosTableModel.setRowCount(0);
        for (Produto p : loja.getProdutos()) {
            produtosTableModel.addRow(new Object[]{p.getId(), p.getNome(), String.format("R$ %.2f", p.getPreco()), p.getEstoque()});
        }
    }

    private void refreshClientes() {
        clientesTableModel.setRowCount(0);
        for (Cliente c : loja.getClientes()) {
            clientesTableModel.addRow(new Object[]{c.getId(), c.getName(), c.getEmail()});
        }
    }

    private void refreshClientesCombo() {
        comboClientes.removeAllItems();
        for (Cliente c : loja.getClientes()) {
            comboClientes.addItem(c.getId() + " - " + c.getName());
        }
    }

    private void refreshProdutosCombo() {
        comboProdutos.removeAllItems();
        for (Produto p : loja.getProdutos()) {
            comboProdutos.addItem(p.getId() + " - " + p.getNome());
        }
    }
}
