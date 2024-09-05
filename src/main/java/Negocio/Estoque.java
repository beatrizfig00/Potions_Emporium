package Negocio;

import java.util.HashMap;
import java.util.Map;

public class Estoque {
    private Map<Produto, Integer> produtos;

    public Estoque() {
        this.produtos = new HashMap<>();
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        produtos.put(produto, produtos.getOrDefault(produto, 0) + quantidade);
    }

    public void removerProduto(Produto produto, int quantidade) throws Exception {
        int quantidadeAtual = produtos.getOrDefault(produto, 0);
        if (quantidadeAtual >= quantidade) {
            if (quantidadeAtual == quantidade) {
                produtos.remove(produto);
            } else {
                produtos.put(produto, quantidadeAtual - quantidade);
            }
        } else {
            throw new Exception("Quantidade insuficiente para remover.");
        }
    }

    public int verificarDisponibilidade(Produto produto) {
        return produtos.getOrDefault(produto, 0);
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }
}
