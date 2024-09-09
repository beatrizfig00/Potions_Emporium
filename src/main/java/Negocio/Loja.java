package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;

import java.util.List;
import java.util.Map;

public class Loja {
    private Estoque estoque;
    private List<Pedido> pedidos;

    public Loja(Estoque estoque, List<Pedido> pedidos) {
        this.estoque = estoque;
        this.pedidos = pedidos;
    }

    public void adicionarProdutoAoEstoque(Produto produto, int quantidade) throws DadosInvalidosException {
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade para adicionar ao estoque não pode ser negativa.");
        }
        estoque.adicionarProduto(produto, quantidade);
    }

    public void removerProdutoDoEstoque(Produto produto, int quantidade) throws DadosInvalidosException, ItemNaoEncontradoException {
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade para remover do estoque não pode ser negativa.");
        }
        estoque.removerProduto(produto, quantidade);
    }

    public int verificarEstoque(Produto produto) {
        try {
            return estoque.getQuantidade(produto);
        } catch (ItemNaoEncontradoException e) {
            return 0;
        }
    }

    public void registrarPedido(Pedido pedido) throws DadosInvalidosException {
        for (Map.Entry<Produto, Integer> entry : pedido.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidadeNoPedido = entry.getValue();
            if (verificarEstoque(produto) < quantidadeNoPedido) {
                throw new DadosInvalidosException(STR."Estoque insuficiente para o produto: \{produto.getNome()}");
            }
        }

        for (Map.Entry<Produto, Integer> entry : pedido.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            try {
                removerProdutoDoEstoque(produto, quantidade);
            } catch (ItemNaoEncontradoException e) {
                System.out.println(STR."Erro ao remover produto do estoque: \{e.getMessage()}");
                throw new DadosInvalidosException(STR."Erro ao remover produto do estoque: \{e.getMessage()}");
            }
        }

        pedidos.add(pedido);
        System.out.println(STR."Pedido registrado com sucesso. Total com desconto: \{pedido.getTotal()}");
    }

    public List<Pedido> getHistoricoPedidos() {
        return pedidos;
    }

    public Map<Produto, Integer> getEstoqueAtual() {
        return estoque.getProdutos();
    }
}
