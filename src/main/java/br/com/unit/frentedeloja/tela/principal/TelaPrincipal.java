package br.com.unit.frentedeloja.tela.principal;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private JTabbedPane abas;

    public TelaPrincipal() {
        setTitle("Sistema Frente de Loja");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        abas = new JTabbedPane();

        abas.addTab("Venda", new PainelVenda());
        abas.addTab("Produtos", new PainelProdutos());
        abas.addTab("Histórico de Vendas", new PainelHistoricoVendas());

        add(abas);

        setVisible(true);
    }
}
