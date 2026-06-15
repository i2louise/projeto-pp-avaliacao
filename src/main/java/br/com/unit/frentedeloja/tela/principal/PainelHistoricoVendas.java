package br.com.unit.frentedeloja.tela.principal;

import br.com.unit.frentedeloja.controller.VendaController;
import br.com.unit.frentedeloja.model.ItemVenda;
import br.com.unit.frentedeloja.model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PainelHistoricoVendas extends JPanel {

    private JTable tabelaVendas;
    private JTable tabelaItens;

    private DefaultTableModel modeloVendas;
    private DefaultTableModel modeloItens;

    private JButton botaoAtualizar;

    private JLabel labelTotalVenda;

    private VendaController vendaController;

    private final Color COR_PRINCIPAL = new Color(75, 0, 130);

    public PainelHistoricoVendas() {
        vendaController = new VendaController();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        montarTopo();
        montarCentro();
        montarRodape();

        carregarVendas();
    }

    private void montarTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_PRINCIPAL);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("HISTÓRICO DE VENDAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Vendas concluídas e seus detalhes");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 14));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setHorizontalAlignment(SwingConstants.RIGHT);

        painelTopo.add(titulo, BorderLayout.WEST);
        painelTopo.add(subtitulo, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);
    }

    private void montarCentro() {
        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelCentro.add(montarTabelaVendas());
        painelCentro.add(montarTabelaItens());

        add(painelCentro, BorderLayout.CENTER);
    }

    private JPanel montarTabelaVendas() {
        JPanel painelVendas = new JPanel(new BorderLayout());
        painelVendas.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Vendas cadastradas");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        modeloVendas = new DefaultTableModel();

        modeloVendas.addColumn("ID");
        modeloVendas.addColumn("CPF");
        modeloVendas.addColumn("Pagamento");
        modeloVendas.addColumn("Data");
        modeloVendas.addColumn("Total");

        tabelaVendas = new JTable(modeloVendas);
        tabelaVendas.setRowHeight(25);
        tabelaVendas.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaVendas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollVendas = new JScrollPane(tabelaVendas);

        painelVendas.add(titulo, BorderLayout.NORTH);
        painelVendas.add(scrollVendas, BorderLayout.CENTER);

        tabelaVendas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarItensVendaSelecionada();
            }
        });

        return painelVendas;
    }

    private JPanel montarTabelaItens() {
        JPanel painelItens = new JPanel(new BorderLayout());
        painelItens.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Detalhes da venda selecionada");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        modeloItens = new DefaultTableModel();

        modeloItens.addColumn("Código");
        modeloItens.addColumn("Produto");
        modeloItens.addColumn("Quantidade");
        modeloItens.addColumn("Preço Unitário");
        modeloItens.addColumn("Subtotal");

        tabelaItens = new JTable(modeloItens);
        tabelaItens.setRowHeight(25);
        tabelaItens.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaItens.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollItens = new JScrollPane(tabelaItens);

        painelItens.add(titulo, BorderLayout.NORTH);
        painelItens.add(scrollItens, BorderLayout.CENTER);

        return painelItens;
    }

    private void montarRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBackground(new Color(240, 240, 240));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        labelTotalVenda = new JLabel("Total da venda selecionada: R$ 0,00");
        labelTotalVenda.setFont(new Font("Arial", Font.BOLD, 22));
        labelTotalVenda.setForeground(COR_PRINCIPAL);

        botaoAtualizar = new JButton("Atualizar Histórico");
        configurarBotao(botaoAtualizar);

        painelRodape.add(labelTotalVenda, BorderLayout.WEST);
        painelRodape.add(botaoAtualizar, BorderLayout.EAST);

        add(painelRodape, BorderLayout.SOUTH);

        botaoAtualizar.addActionListener(e -> carregarVendas());
    }

    private void configurarBotao(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void carregarVendas() {
        modeloVendas.setRowCount(0);
        modeloItens.setRowCount(0);

        labelTotalVenda.setText("Total da venda selecionada: R$ 0,00");

        List<Venda> vendas = vendaController.listarVendas();

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Venda venda : vendas) {
            double total = calcularTotalDaVenda(venda.getIdVenda());

            modeloVendas.addRow(new Object[]{
                    venda.getIdVenda(),
                    venda.getCpfCliente(),
                    venda.getFormaPagamento(),
                    venda.getDataVenda().format(formatoData),
                    "R$ " + formatarValor(total)
            });
        }
    }

    private void carregarItensVendaSelecionada() {
        int linhaSelecionada = tabelaVendas.getSelectedRow();

        if (linhaSelecionada == -1) {
            return;
        }

        modeloItens.setRowCount(0);

        int linhaModelo = tabelaVendas.convertRowIndexToModel(linhaSelecionada);

        int idVenda = Integer.parseInt(
                modeloVendas.getValueAt(linhaModelo, 0).toString()
        );

        List<ItemVenda> itens = vendaController.detalharVenda(idVenda);

        double totalVenda = 0;

        for (ItemVenda item : itens) {
            double subtotal = item.calcularSubtotal();

            modeloItens.addRow(new Object[]{
                    item.getProduto().getCodigoBarras(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    "R$ " + formatarValor(item.getProduto().getPreco()),
                    "R$ " + formatarValor(subtotal)
            });

            totalVenda += subtotal;
        }

        labelTotalVenda.setText(
                "Total da venda selecionada: R$ " + formatarValor(totalVenda)
        );
    }

    private double calcularTotalDaVenda(int idVenda) {
        List<ItemVenda> itens = vendaController.detalharVenda(idVenda);

        double total = 0;

        for (ItemVenda item : itens) {
            total += item.calcularSubtotal();
        }

        return total;
    }

    private String formatarValor(double valor) {
        return String.format("%.2f", valor).replace(".", ",");
    }
}