package Negocio;
/*
import Negocio.produtos.*;
import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;


import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();
        Carrinho carrinho = new Carrinho();
        Cliente cliente = null;
        Pedido pedido = null;
        Caixa caixa = null;

        try {
            System.out.println("Cadastro de Cliente:");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            cliente = new Cliente(nome, "Coruja Padrão", "Pó de Flu Padrão", "senha123", new ArrayList<>());

            System.out.println("Cliente cadastrado com sucesso.");
        } catch (DadosInvalidosException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return;
        }

        try {
            estoque.adicionarProduto(new ProdutoAnimal(1, "Leão", "Animal Selvagem", 200.0, "Animais", "ABC123", 10, "Savana"), 10);
            estoque.adicionarProduto(new ProdutoIngrediente(2, "Sal Grosso", "Ingrediente para tempero", 5.0, "Ingredientes", "DEF456", 50, "Mar"), 50);
            estoque.adicionarProduto(new ProdutoItem(3, "Espada Mágica", "Arma encantada", 150.0, "Armas", "GHI789", 5, "Destruição"), 5);
            estoque.adicionarProduto(new ProdutoLivro(4, "O Grande Livro", "Um livro fantástico", 30.0, "Livros", "JKL012", 20, "Autor Desconhecido", 300), 20);
            estoque.adicionarProduto(new ProdutoPocao(5, "Pocao de Cura", "Restaurar vida", 25.0, "Poções", "MNO345", 15, "Cura", 10), 15);
        } catch (DadosInvalidosException e) {
            System.out.println("Erro ao adicionar produto ao estoque: " + e.getMessage());
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Visualizar produtos disponíveis");
            System.out.println("2. Adicionar produto ao carrinho");
            System.out.println("3. Remover produto do carrinho");
            System.out.println("4. Visualizar carrinho");
            System.out.println("5. Ir para o caixa");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        estoque.listarProdutos();
                        break;
                    case 2:
                        System.out.print("Digite o nome do produto para adicionar: ");
                        String nomeProdutoAdicionar = scanner.nextLine();
                        Produto produtoAdicionar = encontrarProdutoPorNome(estoque, nomeProdutoAdicionar);
                        System.out.print("Digite a quantidade: ");
                        int quantidadeAdicionar = scanner.nextInt();
                        scanner.nextLine();
                        carrinho.adicionarItem(produtoAdicionar, quantidadeAdicionar);
                        System.out.println("Produto adicionado ao carrinho.");
                        break;
                    case 3:
                        System.out.print("Digite o nome do produto para remover: ");
                        String nomeProdutoRemover = scanner.nextLine();
                        Produto produtoRemover = encontrarProdutoPorNome(estoque, nomeProdutoRemover);
                        carrinho.removerItem(produtoRemover);
                        System.out.println("Produto removido do carrinho.");
                        break;
                    case 4:
                        System.out.println("Itens no carrinho:");
                        for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                            System.out.println(item.produto().getNome() + " - Quantidade: " + item.quantidade() + " - Preço: " + item.preco());
                        }
                        break;
                    case 5:
                        Map<Produto, Integer> itensCarrinho = new HashMap<>();
                        for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                            itensCarrinho.put(item.produto(), item.quantidade());
                        }
                        pedido = new Pedido(1, cliente, itensCarrinho, "Em andamento", new Promocao(0));
                        caixa = new Caixa(pedido);

                        double total = caixa.calcularTotal();
                        System.out.println("Total a pagar: " + total);

                        System.out.println("Escolha a forma de pagamento:");
                        System.out.println("1. Galeões");
                        System.out.println("2. Sicles");
                        System.out.println("3. Nuques");
                        System.out.print("Escolha uma opção: ");
                        int formaPagamento = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o valor para pagamento: ");
                        int valor = scanner.nextInt();
                        scanner.nextLine();

                        switch (formaPagamento) {
                            case 1:
                                caixa.processarPagamento(valor, 0, 0);
                                break;
                            case 2:
                                caixa.processarPagamento(0, valor, 0);
                                break;
                            case 3:
                                caixa.processarPagamento(0, 0, valor);
                                break;
                            default:
                                System.out.println("Forma de pagamento inválida.");
                        }

                        System.out.println("Pagamento realizado com sucesso!");

                        System.out.print("Digite sua avaliação (1 a 5): ");
                        int nota = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            Feedback feedback = new Feedback(1, cliente, nota);
                            System.out.println("Obrigado pelo feedback!");
                        } catch (DadosInvalidosException e) {
                            System.out.println("Erro ao registrar feedback: " + e.getMessage());
                        }

                        System.out.println("Compra finalizada.");
                        break;
                    case 6:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Tente novamente.");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static Produto encontrarProdutoPorNome(Estoque estoque, String nome) throws ItemNaoEncontradoException {
        for (Map.Entry<Produto, Integer> entry : estoque.getProdutos().entrySet()) {
            if (entry.getKey().getNome().equalsIgnoreCase(nome)) {
                return entry.getKey();
            }
        }
        throw new ItemNaoEncontradoException("Produto não encontrado: " + nome);
    }
}*/