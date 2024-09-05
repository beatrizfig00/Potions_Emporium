package Negocio;

import java.util.Map;

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private Map<Produto, Integer> itens; // Alterado para Map<Produto, Integer>
    private double total;
    private String statusPedido;

    public Pedido(int idPedido, Cliente cliente, Map<Produto, Integer> itens, String statusPedido) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.itens = itens;
        this.statusPedido = statusPedido;
        this.total = calcularTotal();
    }

    public double calcularTotal() {
        total = 0.0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }

    public void atualizarStatus(String novoStatus) {
        this.statusPedido = novoStatus;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Map<Produto, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Produto, Integer> itens) {
        this.itens = itens;
        this.total = calcularTotal();
    }

    public double getTotal() {
        return total;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public int getQuantidade(Produto produto) {
        return itens.getOrDefault(produto, 0);
    }
}
