package Negocio;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
        if (produto == null || !produtos.containsKey(produto)) {
            throw new DadosInvalidosException("Produto não encontrado no carrinho.");
        }
        int quantidadeAtual = produtos.get(produto);
        if (quantidade <= 0) {
            throw new DadosInvalidosException("Quantidade inválida para remoção.");
        }
        if (quantidadeAtual <= quantidade) {
            produtos.remove(produto);
        } else {
            produtos.put(produto, quantidadeAtual - quantidade);
        }
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public List<ItemCarrinho> getItens() {
        List<ItemCarrinho> itens = new ArrayList<>();
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            double preco = produto.getPreco();
            itens.add(new ItemCarrinho(produto, quantidade, preco));
        }
        return itens;
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
