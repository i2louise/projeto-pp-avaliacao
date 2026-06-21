# Frente de Loja

Sistema desktop de frente de loja desenvolvido para a disciplina de Projeto de Programação. A aplicação permite gerenciar produtos, realizar vendas e consultar o histórico de vendas.

## Funcionalidades

- Cadastro, alteração, remoção e listagem de produtos;
- Associação de imagens aos produtos;
- Controle e atualização de estoque;
- Validação e formatação do CPF do cliente;
- Escolha da forma de pagamento;
- Adição de produtos a venda pelo código de barras;
- Cálculo automático de subtotais e do valor total;
- Conclusão e cancelamento de vendas;
- Consulta ao histórico e aos itens de cada venda.

## Tecnologias utilizadas

- Java 21;
- Java Swing;
- Maven;
- Arquivos CSV para persistência dos dados.

## Estrutura do projeto

```text
projeto-pp-avaliacao
|-- persist
|   |-- produtos.csv
|   |-- vendas.csv
|   `-- itens_venda.csv
|-- resources
|   `-- imagens
|-- src/main/java/br/com/unit/frentedeloja
|   |-- controller
|   |-- model
|   |-- tela
|   |-- util
|   `-- Main.java
`-- pom.xml
```

## Como executar

1. Clone ou baixe este repositorio.
2. Abra o projeto em uma IDE com suporte a Maven, como o IntelliJ IDEA.
3. Configure o projeto para utilizar o JDK 21.
4. Execute a classe `Main.java`.
