package Arquivos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;
import Negocio.produtos.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArquivoEstoque {
    private Map<Produto, Integer> estoque;
    private final String arquivoCSV;

    public ArquivoEstoque(String arquivoCSV) {
        this.arquivoCSV = arquivoCSV;
        this.estoque = new HashMap<>();
        File arquivo = new File(arquivoCSV);

        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
                inicializarCabecalho();
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }

        try {
            carregarProdutos();
        } catch (IOException | DadosInvalidosException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void inicializarCabecalho() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoCSV))) {
            writer.println("ID,Nome,Descricao,Preco,CodigoBarra,Quantidade,TipoProduto,DetalhesEspecificos");
        }
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        estoque.put(produto, quantidade);
    }

    public Map<Produto, Integer> getAllProdutos() {
        return new HashMap<>(estoque);
    }

    public void removerProduto(Produto produto) {
        estoque.remove(produto);
    }

    public Produto getProdutoPorNome(String nome) {
        for (Produto produto : estoque.keySet()) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null;
    }

    public void salvarProdutos() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoCSV, false))) {
            writer.println("ID,Nome,Descricao,Preco,CodigoBarra,Quantidade,TipoProduto,DetalhesEspecificos");
            for (Map.Entry<Produto, Integer> entry : estoque.entrySet()) {
                writer.println(organizarLinhas(entry.getKey(), entry.getValue()));
            }
        }
    }

    public Map<Produto, Integer> carregarProdutos() throws IOException, DadosInvalidosException {
        estoque.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (atributos.length >= 8) {
                    Produto produto = criarProduto(atributos);
                    String quantidadeStr = atributos[5].trim(); // Corrige o índice e o espaçamento
                    int quantidade = parseIntSafe(quantidadeStr, "Quantidade");
                    estoque.put(produto, quantidade);
                } else {
                    System.err.println("Linha com dados insuficientes: " + linha);
                }
            }
        }
        return estoque;
    }

    private String organizarLinhas(Produto produto, int quantidade) {
        String tipoProduto = determinarTipoProduto(produto);
        String detalhesEspecificos = obterDetalhesEspecificos(produto);

        return String.format("%d,%s,%s,%.0f,%s,%d,%s,%s",
                produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),
                produto.getCodigoBarra(), quantidade, tipoProduto, detalhesEspecificos);
    }

    public String determinarTipoProduto(Produto produto) {
        if (produto instanceof ProdutoAnimal) {
            return "Animal";
        } else if (produto instanceof ProdutoIngrediente) {
            return "Ingrediente";
        } else if (produto instanceof ProdutoItem) {
            return "Item";
        } else if (produto instanceof ProdutoLivro) {
            return "Livro";
        } else if (produto instanceof ProdutoPocao) {
            return "Pocao";
        } else {
            return "Desconhecido";
        }
    }

    public String obterDetalhesEspecificos(Produto produto) {
        if (produto instanceof ProdutoAnimal) {
            return ((ProdutoAnimal) produto).getHabitat();
        } else if (produto instanceof ProdutoIngrediente) {
            return ((ProdutoIngrediente) produto).getOrigem();
        } else if (produto instanceof ProdutoItem) {
            return ((ProdutoItem) produto).getPoder();
        } else if (produto instanceof ProdutoLivro) {
            ProdutoLivro livro = (ProdutoLivro) produto;
            return livro.getAutor() + "," + livro.getNumeroPaginas();
        } else if (produto instanceof ProdutoPocao) {
            ProdutoPocao pocao = (ProdutoPocao) produto;
            return pocao.getEfeito() + "," + pocao.getTempoEfeito();
        } else {
            return "";
        }
    }

    private Produto criarProduto(String[] atributos) throws DadosInvalidosException {
        if (atributos.length < 7) {
            throw new DadosInvalidosException("Dados inválidos para criar um produto.");
        }

        int id = parseIntSafe(atributos[0].trim(), "ID");
        String nome = atributos[1].trim();
        String descricao = atributos[2].trim();
        double preco = parseDoubleSafe(atributos[3].trim(), "Preço");
        String codigoBarra = atributos[4].trim();
        int quantidade = parseIntSafe(atributos[5].trim(), "Quantidade");
        String tipoProduto = atributos[6].trim();

        String detalhesEspecificos = atributos.length > 7 ? atributos[7].trim() : "Detalhe não disponível";

        switch (tipoProduto) {
            case "Animal":
                return new ProdutoAnimal(id, nome, descricao, preco, codigoBarra, quantidade, detalhesEspecificos);

            case "Ingrediente":
                return new ProdutoIngrediente(id, nome, descricao, preco, codigoBarra, quantidade, detalhesEspecificos);

            case "Item":
                return new ProdutoItem(id, nome, descricao, preco, codigoBarra, quantidade, detalhesEspecificos);

            case "Livro":
                String[] livroDetalhes = detalhesEspecificos.split(",");
                String autor = livroDetalhes.length > 0 ? livroDetalhes[0] : "Autor Desconhecido";
                int numeroPaginas = livroDetalhes.length > 1 ? parseIntSafe(livroDetalhes[1], "Número de Páginas") : 0;
                return new ProdutoLivro(id, nome, descricao, preco, codigoBarra, quantidade, autor, numeroPaginas);

            case "Pocao":
                String[] pocaoDetalhes = detalhesEspecificos.split(",");
                String efeito = pocaoDetalhes.length > 0 ? pocaoDetalhes[0] : "Efeito Desconhecido";
                int tempoEfeito = pocaoDetalhes.length > 1 ? parseIntSafe(pocaoDetalhes[1], "Tempo de Efeito") : 0;
                return new ProdutoPocao(id, nome, descricao, preco, codigoBarra, quantidade, efeito, tempoEfeito);

            default:
                throw new DadosInvalidosException("Tipo de produto desconhecido: " + tipoProduto);
        }
    }

    private static int parseIntSafe(String valor, String nomeCampo) throws DadosInvalidosException {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new DadosInvalidosException("Formato inválido para o campo " + nomeCampo + ": " + valor);
        }
    }

    private static double parseDoubleSafe(String valor, String nomeCampo) throws DadosInvalidosException {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new DadosInvalidosException("Formato inválido para o campo " + nomeCampo + ": " + valor);
        }
    }

    public void atualizarQuantidade(Produto produto, int novaQuantidade) {
        if (estoque.containsKey(produto)) {
            estoque.put(produto, novaQuantidade);
            try {
                salvarProdutos();
            } catch (IOException e) {
                System.err.println("Erro ao salvar as alterações: " + e.getMessage());
            }
        } else {
            System.err.println("Produto não encontrado no estoque.");
        }
    }

    public int getQuantidadeProduto(Produto produto) {
        return estoque.getOrDefault(produto, 0);

    }
}
