package br.com.unit.frentedeloja.tela.popup;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.model.Produto;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PopupAlterarProduto extends JDialog {

    private JComboBox<Produto> comboProdutos;

    private JTextField campoNome;
    private JTextField campoPreco;
    private JTextField campoEstoque;
    private JTextField campoImagem;

    private JButton botaoSelecionarImagem;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    private ProdutoController produtoController;

    private final Color COR_PRINCIPAL = new Color(75, 0, 130);

    public PopupAlterarProduto(Frame owner) {
        super(owner, "Alterar Produto", true);

        produtoController = new ProdutoController();

        configurarJanela();
        montarTela();
        carregarProdutosNoCombo();

        setVisible(true);
    }

    private void configurarJanela() {
        setSize(520, 320);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void montarTela() {
        add(montarFormulario(), BorderLayout.CENTER);
        add(montarBotoes(), BorderLayout.SOUTH);
    }

    private JPanel montarFormulario() {
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        comboProdutos = new JComboBox<>();

        campoNome = new JTextField();
        campoPreco = new JTextField();
        campoEstoque = new JTextField();

        campoImagem = new JTextField();
        campoImagem.setEditable(false);

        botaoSelecionarImagem = new JButton("Selecionar");

        JPanel painelImagem = new JPanel(new BorderLayout(5, 0));
        painelImagem.add(campoImagem, BorderLayout.CENTER);
        painelImagem.add(botaoSelecionarImagem, BorderLayout.EAST);

        painelFormulario.add(new JLabel("Produto:"));
        painelFormulario.add(comboProdutos);

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Preço:"));
        painelFormulario.add(campoPreco);

        painelFormulario.add(new JLabel("Estoque:"));
        painelFormulario.add(campoEstoque);

        painelFormulario.add(new JLabel("Imagem:"));
        painelFormulario.add(painelImagem);

        comboProdutos.addActionListener(e -> preencherCamposProdutoSelecionado());
        botaoSelecionarImagem.addActionListener(e -> selecionarImagem());

        return painelFormulario;
    }

    private JPanel montarBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        botaoSalvar = new JButton("Salvar Alterações");
        botaoCancelar = new JButton("Cancelar");

        configurarBotaoPrincipal(botaoSalvar);
        configurarBotaoPrincipal(botaoCancelar);

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        botaoSalvar.addActionListener(e -> salvarAlteracoes());
        botaoCancelar.addActionListener(e -> dispose());

        return painelBotoes;
    }

    private void configurarBotaoPrincipal(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void carregarProdutosNoCombo() {
        List<Produto> produtos = produtoController.listarProdutos();

        comboProdutos.removeAllItems();

        for (Produto produto : produtos) {
            comboProdutos.addItem(produto);
        }

        if (produtos.isEmpty()) {
            botaoSalvar.setEnabled(false);
            botaoSelecionarImagem.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Não há produtos cadastrados para alterar.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            preencherCamposProdutoSelecionado();
        }
    }

    private void preencherCamposProdutoSelecionado() {
        Produto produtoSelecionado = (Produto) comboProdutos.getSelectedItem();

        if (produtoSelecionado == null) {
            campoNome.setText("");
            campoPreco.setText("");
            campoEstoque.setText("");
            campoImagem.setText("");
            return;
        }

        campoNome.setText(produtoSelecionado.getNome());
        campoPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
        campoEstoque.setText(String.valueOf(produtoSelecionado.getQtdEstoque()));
        campoImagem.setText(produtoSelecionado.getCaminhoImagem());
    }

    private void selecionarImagem() {
        JFileChooser seletor = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Imagens", "jpg", "jpeg", "png"
        );

        seletor.setFileFilter(filtro);

        int resultado = seletor.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            campoImagem.setText(arquivo.getAbsolutePath());
        }
    }

    private void salvarAlteracoes() {
        Produto produtoSelecionado = (Produto) comboProdutos.getSelectedItem();

        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            String codigo = produtoSelecionado.getCodigoBarras();
            String nome = campoNome.getText().trim();
            String imagem = campoImagem.getText().trim();

            if (nome.isEmpty() || imagem.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            double preco = Double.parseDouble(campoPreco.getText().trim());
            int estoque = Integer.parseInt(campoEstoque.getText().trim());

            if (preco <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O preço deve ser maior que zero.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (estoque < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O estoque não pode ser negativo.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Produto produtoAlterado = new Produto(
                    codigo,
                    nome,
                    preco,
                    estoque,
                    imagem
            );

            boolean alterou = produtoController.alterarProduto(produtoAlterado);

            if (alterou) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado para alteração.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preço e estoque devem ser números válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}