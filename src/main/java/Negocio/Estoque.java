package Negocio;

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

    public void removerProduto(Produto produto, int quantidade) throws Exception {
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
            throw new Exception("Quantidade insuficiente para remover.");
        }
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("O estoque está vazio.");
        } else {
            System.out.println("Produtos no estoque:");
            for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
                Produto produto = entry.getKey();
                int quantidade = entry.getValue();
                System.out.println(STR."\{produto.getNome()} - Quantidade: \{quantidade}");
            }
        }
    }

    public int verificarDisponibilidade(Produto produto) {
        return produtos.getOrDefault(produto, 0);
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }
}
