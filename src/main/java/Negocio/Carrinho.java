package Negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carrinho {
    private List<Produto> itens;
    private Map<Produto, Integer> quantidade;
    private double desconto;

    public Carrinho() {
        this.itens = new ArrayList<>();
        this.quantidade = new HashMap<>();
        this.desconto = 0.0;
    }

    public void adicionarProduto(Produto produto, int qtd) {
        if (quantidade.containsKey(produto)) {
            quantidade.put(produto, quantidade.get(produto) + qtd);
        } else {
            itens.add(produto);
            quantidade.put(produto, qtd);
        }
    }

    public void removerProduto(Produto produto, int qtd) {
        if (quantidade.containsKey(produto)) {
            int quantidadeAtual = quantidade.get(produto);
            if (quantidadeAtual > qtd) {
                quantidade.put(produto, quantidadeAtual - qtd);
            } else {
                itens.remove(produto);
                quantidade.remove(produto);
            }
        } else {
            System.out.println("Produto não encontrado no carrinho.");
        }
    }

    public double calculaTotal() {
        double total = 0.0;
        for (Produto produto : itens) {
            total += produto.getPreco() * quantidade.get(produto);
        }
        total -= desconto;
        return total;
    }

    public void aplicarDesconto(double desconto) {
        this.desconto = desconto;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public Map<Produto, Integer> getQuantidade() {
        return quantidade;
    }

    public void setItens(List<Produto> itens) {
        this.itens = itens;
    }

    public void setQuantidade(Map<Produto, Integer> quantidade) {
        this.quantidade = quantidade;
    }
}
