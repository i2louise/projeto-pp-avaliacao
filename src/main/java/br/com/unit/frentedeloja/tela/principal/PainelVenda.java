package br.com.unit.frentedeloja.tela.principal;

import br.com.unit.frentedeloja.controller.VendaController;
import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelVenda extends JPanel {

    private JTextField campoCpf;
    private JComboBox<String> comboPagamento;

    private JTextField campoCodigoBarras;
    private JTextField campoQuantidade;

    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;

    private JLabel labelTotal;

    private JButton botaoAdicionar;
    private JButton botaoConcluir;
    private JButton botaoCancelar;

    private VendaController vendaController;
    private Venda vendaAtual;

    private final Color COR_PRINCIPAL = new Color(75, 0, 130);

    public PainelVenda() {
        vendaController = new VendaController();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        montarTopo();
        montarCentro();
        montarRodape();
    }

    private void montarTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_PRINCIPAL);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("CAIXA");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        painelTopo.add(titulo, BorderLayout.WEST);

        add(painelTopo, BorderLayout.NORTH);
    }

    private void montarCentro() {
        JPanel painelCentro = new JPanel(new BorderLayout(15, 15));
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelCentro.add(montarFormulario(), BorderLayout.NORTH);
        painelCentro.add(montarTabela(), BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);
    }

    private JPanel montarFormulario() {
        JPanel painelFormulario = new JPanel(new GridLayout(2, 1, 10, 10));
        painelFormulario.setBackground(Color.WHITE);

        JPanel linhaCliente = new JPanel(new GridLayout(1, 4, 10, 0));
        linhaCliente.setBackground(Color.WHITE);

        campoCpf = new JTextField();

        comboPagamento = new JComboBox<>();
        comboPagamento.addItem("PIX");
        comboPagamento.addItem("Débito");
        comboPagamento.addItem("Crédito");
        comboPagamento.addItem("Dinheiro");

        linhaCliente.add(new JLabel("CPF do cliente:"));
        linhaCliente.add(campoCpf);
        linhaCliente.add(new JLabel("Forma de pagamento:"));
        linhaCliente.add(comboPagamento);

        JPanel linhaProduto = new JPanel(new GridLayout(1, 5, 10, 0));
        linhaProduto.setBackground(Color.WHITE);

        campoCodigoBarras = new JTextField();
        campoQuantidade = new JTextField("1");

        botaoAdicionar = new JButton("Adicionar Produto");
        configurarBotao(botaoAdicionar);

        linhaProduto.add(new JLabel("Código de barras:"));
        linhaProduto.add(campoCodigoBarras);
        linhaProduto.add(new JLabel("Quantidade:"));
        linhaProduto.add(campoQuantidade);
        linhaProduto.add(botaoAdicionar);

        painelFormulario.add(linhaCliente);
        painelFormulario.add(linhaProduto);

        botaoAdicionar.addActionListener(e -> adicionarProduto());

        return painelFormulario;
    }

    private JScrollPane montarTabela() {
        modeloTabela = new DefaultTableModel();

        modeloTabela.addColumn("Código");
        modeloTabela.addColumn("Produto");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço Unitário");
        modeloTabela.addColumn("Subtotal");

        tabelaItens = new JTable(modeloTabela);
        tabelaItens.setRowHeight(25);
        tabelaItens.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaItens.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        return new JScrollPane(tabelaItens);
    }

    private void montarRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBackground(new Color(240, 240, 240));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        labelTotal = new JLabel("Total: R$ 0,00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 24));
        labelTotal.setForeground(COR_PRINCIPAL);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setBackground(new Color(240, 240, 240));

        botaoConcluir = new JButton("Concluir Venda");
        botaoCancelar = new JButton("Cancelar Venda");

        configurarBotao(botaoConcluir);
        configurarBotao(botaoCancelar);

        painelBotoes.add(botaoConcluir);
        painelBotoes.add(botaoCancelar);

        painelRodape.add(labelTotal, BorderLayout.WEST);
        painelRodape.add(painelBotoes, BorderLayout.EAST);

        add(painelRodape, BorderLayout.SOUTH);

        botaoConcluir.addActionListener(e -> concluirVenda());
        botaoCancelar.addActionListener(e -> cancelarVenda());
    }

    private void configurarBotao(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void adicionarProduto() {
        try {
            String cpf = campoCpf.getText().trim();
            String formaPagamento = comboPagamento.getSelectedItem().toString();
            String codigoBarras = campoCodigoBarras.getText().trim();
            int quantidade = Integer.parseInt(campoQuantidade.getText().trim());

            if (cpf.isEmpty() || codigoBarras.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha o CPF e o código de barras.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "A quantidade deve ser maior que zero.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (vendaAtual == null) {
                vendaAtual = vendaController.iniciarVenda(cpf, formaPagamento);

                if (vendaAtual == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "CPF inválido.",
                            "Atenção",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                campoCpf.setEnabled(false);
                comboPagamento.setEnabled(false);
            }

            boolean adicionou = vendaController.adicionarProdutoNaVenda(
                    vendaAtual,
                    codigoBarras,
                    quantidade
            );

            if (!adicionou) {
                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado ou estoque insuficiente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            atualizarTabela();

            campoCodigoBarras.setText("");
            campoQuantidade.setText("1");
            campoCodigoBarras.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "A quantidade deve ser um número válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        if (vendaAtual == null) {
            labelTotal.setText("Total: R$ 0,00");
            return;
        }

        for (ItemVenda item : vendaAtual.getItens()) {
            modeloTabela.addRow(new Object[]{
                    item.getProduto().getCodigoBarras(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    "R$ " + formatarValor(item.getProduto().getPreco()),
                    "R$ " + formatarValor(item.calcularSubtotal())
            });
        }

        labelTotal.setText("Total: R$ " + formatarValor(vendaAtual.calcularTotal()));
    }

    private void concluirVenda() {
        if (vendaAtual == null || vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhuma venda foi iniciada.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja concluir esta venda?",
                "Concluir Venda",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            boolean concluiu = vendaController.concluirVenda(vendaAtual);

            if (concluiu) {
                limparVenda();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao concluir a venda.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void cancelarVenda() {
        if (vendaAtual == null) {
            limparVenda();
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja cancelar esta venda?",
                "Cancelar Venda",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            limparVenda();
        }
    }

    private void limparVenda() {
        vendaAtual = null;

        campoCpf.setText("");
        campoCpf.setEnabled(true);

        comboPagamento.setSelectedIndex(0);
        comboPagamento.setEnabled(true);

        campoCodigoBarras.setText("");
        campoQuantidade.setText("1");

        modeloTabela.setRowCount(0);
        labelTotal.setText("Total: R$ 0,00");

        campoCpf.requestFocus();
    }

    private String formatarValor(double valor) {
        return String.format("%.2f", valor).replace(".", ",");
    }
}