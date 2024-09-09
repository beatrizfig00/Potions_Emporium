package Negocio;

import java.util.HashMap;
import java.util.Map;
import Exceptions.DadosInvalidosException;

public class Carrinho {
    private Map<Produto, Integer> produtos;

    public Carrinho() {
        this.produtos = new HashMap<>();
    }

    public void adicionarItem(Produto produto, int quantidade) throws DadosInvalidosException {
        if (produto == null || quantidade <= 0) {
            throw new DadosInvalidosException("Produto ou quantidade inválidos.");
        }
        produtos.put(produto, produtos.getOrDefault(produto, 0) + quantidade);
    }

    public void removerItem(Produto produto, int quantidade) throws DadosInvalidosException {
        if (produto == null || quantidade <= 0 || !produtos.containsKey(produto)) {
            throw new DadosInvalidosException("Produto ou quantidade inválidos.");
        }
        int quantidadeAtual = produtos.get(produto);
        if (quantidadeAtual <= quantidade) {
            produtos.remove(produto);
        } else {
            produtos.put(produto, quantidadeAtual - quantidade);
        }
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public static class ItemCarrinho {
        private Produto produto;
        private int quantidade;
        private double preco;

        public ItemCarrinho(Produto produto, int quantidade, double preco) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.preco = preco;
        }

        public Produto getProduto() {
            return produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public double getPreco() {
            return preco;
        }
    }
}
