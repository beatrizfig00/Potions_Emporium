package Arquivos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;
import Negocio.produtos.*;
import java.io.*;
import java.util.ArrayList;
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
        return estoque;
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
            while ((linha = reader.readLine()) != null) {
                Produto produto = criarProduto(linha);
                int quantidade = Integer.parseInt(linha.split(",")[7]);
                estoque.put(produto, quantidade);
            }
        }
        return estoque;
    }

    private static String organizarLinhas(Produto produto, int quantidade) {
        return String.format("%d,%s,%s,%f,%s,%s,%d,%s",
                produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),
                produto.getCategoria(), produto.getCodigoBarra(), quantidade,
                produto instanceof ProdutoAnimal ? ((ProdutoAnimal) produto).getHabitat() :
                        produto instanceof ProdutoIngrediente ? ((ProdutoIngrediente) produto).getOrigem() :
                                produto instanceof ProdutoItem ? ((ProdutoItem) produto).getPoder() :
                                        produto instanceof ProdutoLivro ? ((ProdutoLivro) produto).getAutor() + "," + ((ProdutoLivro) produto).getNumeroPaginas() :
                                                produto instanceof ProdutoPocao ? ((ProdutoPocao) produto).getEfeito() + "," + ((ProdutoPocao) produto).getTempoefeito() : "N/A"
        );
    }

    private static Produto criarProduto(String linha) throws DadosInvalidosException {
        String[] atributos = linha.split(",");
        if (atributos.length < 8) {
            throw new DadosInvalidosException("Dados inválidos para criar um produto.");
        }

        int id = Integer.parseInt(atributos[0]);
        String nome = atributos[1];
        String descricao = atributos[2];
        double preco = Double.parseDouble(atributos[3]);
        String categoria = atributos[4];
        String codigoBarra = atributos[5];
        int quantidade = Integer.parseInt(atributos[6]);

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
                int numeroPaginas = Integer.parseInt(atributos[9]);
                return new ProdutoLivro(id, nome, descricao, preco, categoria, codigoBarra, quantidade, autor, numeroPaginas);
            case "Pocao":
                String efeito = atributos[8];
                int tempoefeito = Integer.parseInt(atributos[9]);
                return new ProdutoPocao(id, nome, descricao, preco, categoria, codigoBarra, quantidade, efeito, tempoefeito);
            default:
                throw new DadosInvalidosException("Tipo de produto desconhecido.");
        }
    }
}
