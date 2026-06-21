package br.com.unit.frentedeloja.tela.popup;

import br.com.unit.frentedeloja.controller.ProdutoController;
import br.com.unit.frentedeloja.model.Produto;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PopupCadastrarProduto extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoPreco;
    private JTextField campoEstoque;
    private JTextField campoImagem;

    private JButton botaoSelecionarImagem;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    private ProdutoController produtoController;

    public PopupCadastrarProduto(Frame owner) {
        super(owner, "Cadastrar Produto", true);

        produtoController = new ProdutoController();

        configurarJanela();
        montarTela();

        setVisible(true);
    }

    private void configurarJanela() {
        setSize(500, 350);
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

        campoCodigo = new JTextField();
        campoNome = new JTextField();
        campoPreco = new JTextField();
        campoEstoque = new JTextField();

        campoImagem = new JTextField();
        campoImagem.setEditable(false);

        botaoSelecionarImagem = new JButton("Selecionar");

        JPanel painelImagem = new JPanel(new BorderLayout(5, 0));
        painelImagem.add(campoImagem, BorderLayout.CENTER);
        painelImagem.add(botaoSelecionarImagem, BorderLayout.EAST);

        painelFormulario.add(new JLabel("Código de barras:"));
        painelFormulario.add(campoCodigo);

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Preço:"));
        painelFormulario.add(campoPreco);

        painelFormulario.add(new JLabel("Estoque:"));
        painelFormulario.add(campoEstoque);

        painelFormulario.add(new JLabel("Imagem:"));
        painelFormulario.add(painelImagem);

        botaoSelecionarImagem.addActionListener(e -> selecionarImagem());

        return painelFormulario;
    }

    private JPanel montarBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        configurarBotao(botaoSalvar);
        configurarBotao(botaoCancelar);

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        botaoSalvar.addActionListener(e -> salvarProduto());
        botaoCancelar.addActionListener(e -> dispose());

        return painelBotoes;
    }

    private void configurarBotao(JButton botao) {
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(new Color(75, 0, 130));
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void selecionarImagem() {
        JFileChooser seletor = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Imagens", "jpg", "jpeg", "png"
        );

        seletor.setFileFilter(filtro);

        int resultado = seletor.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File arquivoSelecionado = seletor.getSelectedFile();

                File pastaImagens = new File("resources/imagens");

                if (!pastaImagens.exists() && !pastaImagens.mkdirs()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Não foi possível criar a pasta de imagens.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                String nomeArquivo = arquivoSelecionado.getName();

                Path origem = arquivoSelecionado.toPath();
                Path destino = Path.of("resources", "imagens", nomeArquivo);

                Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

                String caminhoRelativo = "resources/imagens/" + nomeArquivo;

                campoImagem.setText(caminhoRelativo);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao copiar a imagem.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void salvarProduto() {
        try {
            String codigo = campoCodigo.getText().trim();
            String nome = campoNome.getText().trim();
            String imagem = campoImagem.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || imagem.isEmpty()) {
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

            Produto produto = new Produto(
                    codigo,
                    nome,
                    preco,
                    estoque,
                    imagem
            );

            boolean cadastrou = produtoController.cadastrarProduto(produto);

            if (cadastrou) {
                JOptionPane.showMessageDialog(
                        this,
                        "Produto cadastrado com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um produto com esse código de barras.",
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