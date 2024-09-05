package Negocio;

import java.util.List;
import java.util.Map;

public class Loja {
    private Estoque estoque;
    private List<Pedido> pedidos;

    public Loja(Estoque estoque, List<Pedido> pedidos) {
        this.estoque = estoque;
        this.pedidos = pedidos;
    }

    public void adicionarProdutoAoEstoque(Produto produto, int quantidade) {
        estoque.adicionarProduto(produto, quantidade);
    }

    public void removerProdutoDoEstoque(Produto produto, int quantidade) throws Exception {
        estoque.removerProduto(produto, quantidade);
    }

    public int verificarEstoque(Produto produto) {
        return estoque.verificarDisponibilidade(produto);
    }

    public void registrarPedido(Pedido pedido) throws Exception {
        for (Map.Entry<Produto, Integer> entry : pedido.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidadeNoPedido = entry.getValue();
            if (verificarEstoque(produto) < quantidadeNoPedido) {
                throw new Exception("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        for (Map.Entry<Produto, Integer> entry : pedido.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            removerProdutoDoEstoque(produto, quantidade);
        }

        pedidos.add(pedido);
    }

    public List<Pedido> getHistoricoPedidos() {
        return pedidos;
    }

    public Map<Produto, Integer> getEstoqueAtual() {
        return estoque.getProdutos();
    }
}
