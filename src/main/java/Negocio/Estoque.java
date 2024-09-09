package Negocio;

import Exceptions.ItemNaoEncontradoException;

import java.util.HashMap;
import java.util.Map;

public class Estoque {
    private final Map<Produto, Integer> produtos;

    public Estoque() {
        this.produtos = new HashMap<>();
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        produtos.put(produto, produtos.getOrDefault(produto, 0) + quantidade);
        System.out.println(STR."Produto adicionado: \{produto.getNome()} - Quantidade: \{quantidade}");
    }

    public void removerProduto(Produto produto, int quantidade) throws ItemNaoEncontradoException {
        int quantidadeAtual = produtos.getOrDefault(produto, 0);
        if (quantidadeAtual >= quantidade) {
            if (quantidadeAtual == quantidade) {
                produtos.remove(produto);
                System.out.println(STR."Produto removido: \{produto.getNome()}");
            } else {
                produtos.put(produto, quantidadeAtual - quantidade);
                System.out.println(STR."Produto atualizado: \{produto.getNome()} - Quantidade restante: \{quantidadeAtual - quantidade}");
            }
        } else {
            throw new ItemNaoEncontradoException("Quantidade insuficiente para remover.");
        }
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("O estoque está vazio.");
        } else {
            System.out.println("Produtos no estoque:");
            for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
                Produto produto = entry.getKey();
                Integer quantidade = entry.getValue();
                System.out.println(STR."Produto: \{produto.getNome()} - Quantidade: \{quantidade}");
            }
        }
    }

    public int getQuantidade(Produto produto) throws ItemNaoEncontradoException {
        if (produtos.containsKey(produto)) {
            return produtos.get(produto);
        } else {
            throw new ItemNaoEncontradoException("Produto não encontrado no estoque.");
        }
    }

    public Map<Produto, Integer> getProdutos() {
        return new HashMap<>(produtos);
    }
}
