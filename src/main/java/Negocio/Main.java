package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;
import Negocio.produtos.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();
        Carrinho carrinho = new Carrinho();
        Cliente cliente = null;

        while (cliente == null) {
            try {
                cliente = cadastrarCliente(scanner);
                System.out.println("Cliente cadastrado com sucesso.");
            } catch (DadosInvalidosException e) {
                System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            }
        }

        boolean running = true;
        while (running) {
            try {
                switch (mostrarMenu(scanner)) {
                    case 1:
                        visualizarProdutosDisponiveis(scanner, estoque);
                        break;
                    case 2:
                        adicionarProdutoCarrinho(scanner, estoque, carrinho);
                        break;
                    case 3:
                        removerProdutoCarrinho(scanner, carrinho);
                        break;
                    case 4:
                        visualizarCarrinho(carrinho);
                        break;
                    case 5:
                        realizarPagamento(scanner, carrinho, cliente);
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

    private static Cliente cadastrarCliente(Scanner scanner) throws DadosInvalidosException {
        System.out.println("---- Cadastro de Cliente ----");

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

        return new Cliente(nome, coruja, flooPowder, senha);
    }

    private static int mostrarMenu(Scanner scanner) {
        System.out.println("\nMenu:");
        System.out.println("1. Visualizar produtos disponíveis");
        System.out.println("2. Adicionar produto ao carrinho");
        System.out.println("3. Remover produto do carrinho");
        System.out.println("4. Visualizar carrinho");
        System.out.println("5. Ir para o caixa");
        System.out.println("6. Sair");
        System.out.print("\nEscolha uma opção: ");
        return scanner.nextInt();
    }

    private static void visualizarProdutosDisponiveis(Scanner scanner, Estoque estoque) {
        try {
            System.out.println("\nProdutos Disponíveis:");
            estoque.adicionarProduto(new ProdutoAnimal(1, "Hipogrifo", "Criatura mágica alada", 500.0, "HP001", 5, "Floresta Proibida"), 5);
            estoque.adicionarProduto(new ProdutoAnimal(2, "Dragão Húngaro", "Dragão com escamas douradas", 1500.0, "HP002", 3, "Montanhas"), 3);
            estoque.adicionarProduto(new ProdutoIngrediente(3, "Mandrágora", "Raiz utilizada em poções", 200.0, "HP003", 10, "Jardim de Herbologia"), 10);
            estoque.adicionarProduto(new ProdutoIngrediente(4, "Pó de Flu", "Pó mágico para viagem", 50.0, "HP004", 20, "Loja de Poções"), 20);
            estoque.adicionarProduto(new ProdutoItem(5, "Varinha de Sabugueiro", "Varinha mágica potente", 300.0, "HP005", 7, "Destruição de Horcruxes"), 7);
            estoque.adicionarProduto(new ProdutoLivro(6, "Harry Potter e a Pedra Filosofal", "Primeiro livro da série", 80.0, "HP006", 15, "J.K. Rowling", 223), 15);
            estoque.adicionarProduto(new ProdutoPocao(7, "Poçao Polissuco", "Poçao que permite transformação temporária", 150.0, "HP007", 15, "Transformação Temporária", 60), 15);
            estoque.adicionarProduto(new ProdutoItem(8, "Capa de Invisibilidade", "Capa que torna invisível", 1000.0, "HP008", 2, "Invisibilidade"), 2);

            System.out.println("\nDigite 'M' para voltar ao menu.");
            String input = scanner.next();
            if (input.equalsIgnoreCase("M")) {
                return;
            }
        } catch (Exception e) {
            System.out.println("Erro ao visualizar produtos: " + e.getMessage());
        }
    }

    private static void adicionarProdutoCarrinho(Scanner scanner, Estoque estoque, Carrinho carrinho) {
        boolean continuarAdicionando = true;

        while (continuarAdicionando) {
            try {
                System.out.println("\nProdutos Disponíveis:");
                List<Produto> produtosDisponiveis = new ArrayList<>(estoque.getProdutos().keySet());
                for (int i = 0; i < produtosDisponiveis.size(); i++) {
                    Produto produto = produtosDisponiveis.get(i);
                    System.out.println((i + 1) + ". " + produto.getNome() + " - Preço: " + produto.getPreco() + " - Quantidade disponível: " + estoque.getQuantidadeProduto(produto));
                }

                System.out.print("\nEscolha o número do produto para adicionar ao carrinho (ou digite 0 para voltar ao menu): ");
                int escolhaProduto = scanner.nextInt();

                if (escolhaProduto == 0) {
                    return;
                }

                if (escolhaProduto < 1 || escolhaProduto > produtosDisponiveis.size()) {
                    System.out.println("Escolha inválida. Tente novamente.");
                    continue;
                }

                Produto produtoAdicionar = produtosDisponiveis.get(escolhaProduto - 1);

                System.out.print("\nDigite a quantidade (ou digite 0 para cancelar): ");
                int quantidadeAdicionar = scanner.nextInt();

                if (quantidadeAdicionar == 0) {
                    continue;
                }

                int quantidadeDisponivel = estoque.getQuantidadeProduto(produtoAdicionar);

                if (quantidadeAdicionar > quantidadeDisponivel) {
                    System.out.println("\nQuantidade solicitada maior do que a disponível no estoque. Quantidade disponível: " + quantidadeDisponivel);
                    continue;
                }

                carrinho.adicionarItem(produtoAdicionar, quantidadeAdicionar);
                estoque.removerProduto(produtoAdicionar, quantidadeAdicionar);
                System.out.println("\nProduto adicionado ao carrinho.");

                System.out.print("\nDeseja adicionar mais produtos? (s/n): ");
                scanner.nextLine();
                String resposta = scanner.nextLine();

                if (resposta.equalsIgnoreCase("n")) {
                    continuarAdicionando = false;
                }

            } catch (Exception e) {
                System.out.println("Erro ao adicionar produto ao carrinho: " + e.getMessage());
            }
        }
    }


    private static void removerProdutoCarrinho(Scanner scanner, Carrinho carrinho) {
        try {
            System.out.println("\nItens no carrinho:");
            List<Carrinho.ItemCarrinho> itensCarrinho = carrinho.getItens();
            for (int i = 0; i < itensCarrinho.size(); i++) {
                Carrinho.ItemCarrinho item = itensCarrinho.get(i);
                System.out.println((i + 1) + ". " + item.getProduto().getNome() + " - Quantidade: " + item.getQuantidade() + " - Preço: " + item.getPreco());
            }

            System.out.print("\nEscolha o número do produto para remover do carrinho (ou digite 0 para voltar ao menu): ");
            int escolhaProduto = scanner.nextInt();

            if (escolhaProduto == 0) {
                return;
            }

            if (escolhaProduto < 1 || escolhaProduto > itensCarrinho.size()) {
                System.out.println("\nEscolha inválida. Tente novamente.");
                return;
            }

            Carrinho.ItemCarrinho itemRemover = itensCarrinho.get(escolhaProduto - 1);
            Produto produtoRemover = itemRemover.getProduto();

            System.out.print("\nDigite a quantidade para remover (ou digite 0 para cancelar): ");
            int quantidadeRemover = scanner.nextInt();

            if (quantidadeRemover == 0) {
                return;
            }

            int quantidadeNoCarrinho = carrinho.getProdutos().getOrDefault(produtoRemover, 0);
            if (quantidadeRemover > quantidadeNoCarrinho) {
                System.out.println("\nQuantidade solicitada maior do que a disponível no carrinho. Quantidade no carrinho: " + quantidadeNoCarrinho);
                return;
            }

            carrinho.removerItem(produtoRemover, quantidadeRemover);
            System.out.println("\nProduto removido do carrinho.");

        } catch (Exception e) {
            System.out.println("Erro ao remover produto do carrinho: " + e.getMessage());
        }
    }

    private static void visualizarCarrinho(Carrinho carrinho) {
        try {
            System.out.println("\nItens no carrinho:");
            double totalCarrinho = 0;
            for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                System.out.println(item.getProduto().getNome() + " - Quantidade: " + item.getQuantidade() + " - Preço: " + item.getPreco());
                totalCarrinho += item.getPreco();
            }
            System.out.printf("\nTotal do carrinho: %.2f Nuques%n", totalCarrinho);

            System.out.println("\nDigite 'M' para voltar ao menu.");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("M")) {
                return;
            }
        } catch (Exception e) {
            System.out.println("\nErro ao visualizar carrinho: " + e.getMessage());
        }
    }

    private static void realizarPagamento(Scanner scanner, Carrinho carrinho, Cliente cliente) {
        try {
            Map<Produto, Integer> itensCarrinhoMap = new HashMap<>();
            for (Carrinho.ItemCarrinho item : carrinho.getItens()) {
                itensCarrinhoMap.put(item.getProduto(), item.getQuantidade());
            }

            Promocao promocao = verificarPromocao(cliente);
            Pedido pedido = new Pedido(1, cliente, itensCarrinhoMap, "Em andamento", promocao);
            Caixa caixa = new Caixa(pedido);

            double total = caixa.calcularTotal();
            System.out.printf("Total a pagar: %.2f Nuques%n", total);

            boolean pagamentoRealizado = false;

            while (!pagamentoRealizado) {
                try {
                    System.out.println("\nEscolha a forma de pagamento:");
                    System.out.println("1. Galeões");
                    System.out.println("2. Sicles");
                    System.out.println("3. Nuques");
                    System.out.print("Escolha uma opção: ");
                    int formaPagamento = scanner.nextInt();

                    System.out.print("\nDigite o valor para pagamento: ");
                    double valor = scanner.nextDouble();

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
                            System.out.println("\nForma de pagamento inválida.");
                            continue;
                    }

                    caixa.processarPagamento(galeoes, sicles, nuques);
                    pagamentoRealizado = caixa.getTotal() <= 0;
                    if (pagamentoRealizado) {
                        System.out.println("\nPagamento realizado com sucesso!");
                        cliente.adicionarPedido(pedido);
                    } else {
                        System.out.println("\nPagamento insuficiente. Tente novamente.");
                    }
                } catch (Exception e) {
                    System.out.println("\nErro ao processar pagamento: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("\nErro ao realizar pagamento: " + e.getMessage());
        }
    }

    private static Promocao verificarPromocao(Cliente cliente) throws DadosInvalidosException {
        int numeroCompras = cliente.getHistoricoCompras().size();
        if (numeroCompras >= 3) {
            return new Promocao(10); // 10% de desconto
        } else if (numeroCompras >= 1) {
            return new Promocao(5); // 5% de desconto
        } else {
            return new Promocao(0); // Sem desconto
        }
    }
}
