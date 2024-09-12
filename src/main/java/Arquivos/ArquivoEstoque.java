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
        this.estoque = new HashMap<>();
        this.arquivoCSV = arquivoCSV;
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
                Produto produto = criarProduto(linha);
                int quantidade = parseIntSafe(linha.split(",")[6], "Quantidade");
                estoque.put(produto, quantidade);
            }
        }
        return new HashMap<>(estoque);
    }

    private static String organizarLinhas(Produto produto, int quantidade) {
        String tipoProduto = "N/A";
        String detalhesEspecificos = "";

        if (produto instanceof ProdutoAnimal) {
            tipoProduto = "Animal";
            detalhesEspecificos = ((ProdutoAnimal) produto).getHabitat();
        } else if (produto instanceof ProdutoIngrediente) {
            tipoProduto = "Ingrediente";
            detalhesEspecificos = ((ProdutoIngrediente) produto).getOrigem();
        } else if (produto instanceof ProdutoItem) {
            tipoProduto = "Item";
            detalhesEspecificos = ((ProdutoItem) produto).getPoder();
        } else if (produto instanceof ProdutoLivro) {
            ProdutoLivro livro = (ProdutoLivro) produto;
            tipoProduto = "Livro";
            detalhesEspecificos = livro.getAutor() + "," + livro.getNumeroPaginas();
        } else if (produto instanceof ProdutoPocao) {
            ProdutoPocao pocao = (ProdutoPocao) produto;
            tipoProduto = "Pocao";
            detalhesEspecificos = pocao.getEfeito() + "," + pocao.getTempoefeito();
        }

        return String.format("%d,%s,%s,%.2f,%s,%s,%d,%s,%s",
                produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),
                produto.getCategoria(), produto.getCodigoBarra(), quantidade, tipoProduto, detalhesEspecificos);
    }

    private static Produto criarProduto(String linha) throws DadosInvalidosException {
        String[] atributos = linha.split(",");
        if (atributos.length < 8) {
            throw new DadosInvalidosException("Dados inválidos para criar um produto.");
        }

        int id = parseIntSafe(atributos[0], "ID");
        String nome = atributos[1];
        String descricao = atributos[2];
        double preco = parseDoubleSafe(atributos[3], "Preço");
        String categoria = atributos[4];
        String codigoBarra = atributos[5];
        int quantidade = parseIntSafe(atributos[6], "Quantidade");
        String tipoProduto = atributos[7];

        switch (tipoProduto) {
            case "Animal":
                String habitat = atributos[8];
                return new ProdutoAnimal(id, nome, descricao, preco, categoria, codigoBarra, quantidade, habitat);
            case "Ingrediente":
                String origem = atributos[8];
                return new ProdutoIngrediente(id, nome, descricao, preco, categoria, codigoBarra, quantidade, origem);
            case "Item":
                String poder = atributos[8];
                return new ProdutoItem(id, nome, descricao, preco, categoria, codigoBarra, quantidade, poder);
            case "Livro":
                String autor = atributos[8];
                int numeroPaginas = parseIntSafe(atributos[9], "Número de Páginas");
                return new ProdutoLivro(id, nome, descricao, preco, categoria, codigoBarra, quantidade, autor, numeroPaginas);
            case "Pocao":
                String efeito = atributos[8];
                int tempoefeito = parseIntSafe(atributos[9], "Tempo de Efeito");
                return new ProdutoPocao(id, nome, descricao, preco, categoria, codigoBarra, quantidade, efeito, tempoefeito);
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
}