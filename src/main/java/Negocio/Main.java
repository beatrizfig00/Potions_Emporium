package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;
import Negocio.produtos.ProdutoAnimal;
import Negocio.produtos.ProdutoIngrediente;
import Negocio.produtos.ProdutoItem;
import Negocio.produtos.ProdutoLivro;
import Negocio.produtos.ProdutoPocao;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();
        Carrinho carrinho = new Carrinho();
        Cliente cliente = null;
        Pedido pedido = null;
        Caixa caixa = null;

        while (cliente == null) {
            try {
                System.out.println("----Cadastro de Cliente----");

                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                if (nome.isBlank()) {
                    throw new DadosInvalidosException("Nome não pode ser vazio.");
                }

                System.out.print("Coruja: ");
                String coruja = scanner.nextLine();
                if (coruja.isBlank() || !coruja.matches("\\d+")) {
                    throw new DadosInvalidosException("Coruja deve conter apenas números e não pode ser vazio.");
                }

                System.out.print("Floopowder: ");
                String flooPowder = scanner.nextLine();
                if (flooPowder.isBlank()) {
                    throw new DadosInvalidosException("Floopowder não pode ser vazio.");
                }

                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                if (senha.isBlank()) {
                    throw new DadosInvalidosException("Senha não pode ser vazia.");
                }

                cliente = new Cliente(nome, coruja, flooPowder, senha);
                System.out.println("Cliente cadastrado com sucesso.");

            } catch (DadosInvalidosException e) {
                System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            }
        }




        boolean running = true;
        while (running) {
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
                        while (true) {
                            try {
                                estoque.adicionarProduto(new ProdutoAnimal(1, "Hipogrifo", "Criatura mágica alada", 500.0, "HP001", 5, "Floresta Proibida"), 5);
                                estoque.adicionarProduto(new ProdutoAnimal(2, "Dragão Húngaro", "Dragão com escamas douradas", 1500.0, "HP002", 3, "Montanhas"), 3);
                                estoque.adicionarProduto(new ProdutoIngrediente(3, "Mandrágora", "Raiz utilizada em poções", 200.0, "HP003", 10, "Jardim de Herbologia"), 10);
                                estoque.adicionarProduto(new ProdutoIngrediente(4, "Pó de Flu", "Pó mágico para viagem", 50.0, "HP004", 20, "Loja de Poções"), 20);
                                estoque.adicionarProduto(new ProdutoItem(5, "Varinha de Sabugueiro", "Varinha mágica potente", 300.0, "HP005", 7, "Destruição de Horcruxes"), 7);
                                estoque.adicionarProduto(new ProdutoLivro(6, "Harry Potter e a Pedra Filosofal", "Primeiro livro da série", 80.0, "HP006", 15, "J.K. Rowling", 223), 15);
                                estoque.adicionarProduto(new ProdutoPocao(7, "Poçao Polissuco", "Poçao que permite transformação temporária", 150.0, "HP007", 15, "Transformação Temporária", 60), 15);
                                estoque.adicionarProduto(new ProdutoItem(8, "Capa de Invisibilidade", "Capa que torna invisível", 1000.0, "HP008", 2, "Invisibilidade"), 2);
                            } catch (DadosInvalidosException e) {
                                System.out.println("Erro ao adicionar produto ao estoque: " + e.getMessage());
                            }
                            System.out.println("\nDigite 'M' para voltar ao menu.");
                            String input = scanner.nextLine();
                            if (input.equalsIgnoreCase("M")) {
                                break;
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            try {
                                System.out.println("Produtos disponíveis:");
                                List<Produto> produtosDisponiveis = new ArrayList<>(estoque.getProdutos().keySet());
                                for (int i = 0; i < produtosDisponiveis.size(); i++) {
                                    Produto produto = produtosDisponiveis.get(i);
                                    System.out.println((i + 1) + ". " + produto.getNome() + " - Preço: " + produto.getPreco() + " - Quantidade disponível: " + estoque.getQuantidadeProduto(produto));
                                }

                                System.out.print("Escolha o número do produto para adicionar ao carrinho (ou digite 0 para voltar ao menu): ");
                                int escolhaProduto = scanner.nextInt();
                                scanner.nextLine();

                                if (escolhaProduto == 0) {
                                    break;
                                }

                                if (escolhaProduto < 1 || escolhaProduto > produtosDisponiveis.size()) {
                                    System.out.println("Escolha inválida. Tente novamente.");
                                    continue;
                                }

                                Produto produtoAdicionar = produtosDisponiveis.get(escolhaProduto - 1);

                                System.out.print("Digite a quantidade (ou digite 0 para cancelar): ");
                                int quantidadeAdicionar = scanner.nextInt();
                                scanner.nextLine();

                                if (quantidadeAdicionar == 0) {
                                    continue;
                                }

                                int quantidadeDisponivel = estoque.getQuantidadeProduto(produtoAdicionar);

                                if (quantidadeAdicionar > quantidadeDisponivel) {
                                    System.out.println("Quantidade solicitada maior do que a disponível no estoque. Quantidade disponível: " + quantidadeDisponivel);
                                    continue;
                                }

                                carrinho.adicionarItem(produtoAdicionar, quantidadeAdicionar);
                                estoque.removerProduto(produtoAdicionar, quantidadeAdicionar);
                                System.out.println("Produto adicionado ao carrinho.");

                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;


                    case 3:
                        while (true) {
                            try {
                                System.out.println("Itens no carrinho:");
                                List<Carrinho.ItemCarrinho> itensCarrinho = carrinho.getItens();
                                for (int i = 0; i < itensCarrinho.size(); i++) {
                                    Carrinho.ItemCarrinho item = itensCarrinho.get(i);
                                    System.out.println((i + 1) + ". " + item.getProduto().getNome() + " - Quantidade: " + item.getQuantidade() + " - Preço: " + item.getPreco());
                                }

                                System.out.print("Escolha o número do produto para remover do carrinho (ou digite 0 para voltar ao menu): ");
                                int escolhaProduto = scanner.nextInt();
                                scanner.nextLine();

                                if (escolhaProduto == 0) {
                                    break;
                                }

                                if (escolhaProduto < 1 || escolhaProduto > itensCarrinho.size()) {
                                    System.out.println("Escolha inválida. Tente novamente.");
                                    continue;
                                }

                                Carrinho.ItemCarrinho itemRemover = itensCarrinho.get(escolhaProduto - 1);
                                Produto produtoRemover = itemRemover.getProduto();

                                System.out.print("Digite a quantidade para remover (ou digite 0 para cancelar): ");
                                int quantidadeRemover = scanner.nextInt();
                                scanner.nextLine();

                                if (quantidadeRemover == 0) {
                                    continue;
                                }

                                int quantidadeNoCarrinho = carrinho.getProdutos().getOrDefault(produtoRemover, 0);
                                if (quantidadeRemover > quantidadeNoCarrinho) {
                                    System.out.println("Quantidade solicitada maior do que a disponível no carrinho. Quantidade no carrinho: " + quantidadeNoCarrinho);
                                    continue;
                                }

                                carrinho.removerItem(produtoRemover, quantidadeRemover);
                                System.out.println("Produto removido do carrinho.");

                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;


                    case 4:
                        while (true) {
                            System.out.println("Itens no carrinho:");
                            for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                                System.out.println(item.getProduto().getNome() + " - Quantidade: " + item.getQuantidade() + " - Preço: " + item.getPreco());
                            }

                            System.out.println("\nDigite 'M' para voltar ao menu.");
                            String input = scanner.nextLine();
                            if (input.equalsIgnoreCase("M")) {
                                break;
                            }
                        }
                        break;
                    case 5:
                        Map<Produto, Integer> itensCarrinho = new HashMap<>();
                        for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                            itensCarrinho.put(item.getProduto(), item.getQuantidade());
                        }
                        pedido = new Pedido(1, cliente, itensCarrinho, "Em andamento", new Promocao(0));
                        caixa = new Caixa(pedido);

                        double total = caixa.calcularTotal();
                        System.out.printf("Total a pagar: %.2f Nuques%n", total);

                        boolean pagamentoRealizado = false;

                        while (!pagamentoRealizado) {
                            try {
                                System.out.println("Escolha a forma de pagamento:");
                                System.out.println("1. Galeões");
                                System.out.println("2. Sicles");
                                System.out.println("3. Nuques");
                                System.out.print("Escolha uma opção: ");
                                int formaPagamento = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Digite o valor para pagamento: ");
                                double valor = scanner.nextDouble();
                                scanner.nextLine();

                                int galeoes = 0, sicles = 0, nuques = 0;

                                switch (formaPagamento) {
                                    case 1:
                                        galeoes = (int) valor;
                                        break;
                                    case 2:
                                        sicles = (int) valor;
                                        break;
                                    case 3:
                                        nuques = (int) valor;
                                        break;
                                    default:
                                        System.out.println("Forma de pagamento inválida.");
                                        continue;
                                }

                                caixa.processarPagamento(galeoes, sicles, nuques);
                                pagamentoRealizado = caixa.getTotal() <= 0;
                                if (pagamentoRealizado) {
                                    System.out.println("Pagamento realizado com sucesso!");
                                } else {
                                    System.out.println("Pagamento insuficiente. Tente novamente.");
                                }
                            } catch (Exception e) {
                                System.out.println("Erro ao processar pagamento: " + e.getMessage());
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Saindo...");
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
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

    private static Produto encontrarProdutoNoCarrinho(Carrinho carrinho, String nome) throws ItemNaoEncontradoException {
        for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getNome().equalsIgnoreCase(nome)) {
                return item.getProduto();
            }
        }
        throw new ItemNaoEncontradoException("Produto não encontrado no carrinho: " + nome);
    }
}
