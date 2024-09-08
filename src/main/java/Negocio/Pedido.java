package Negocio;

import java.util.Map;

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private Map<Produto, Integer> itens;
    private double total;
    private String statusPedido;
    private Promocao promocao;
    private Entrega entrega;

    public Pedido(int idPedido, Cliente cliente, Map<Produto, Integer> itens, String statusPedido, Promocao promocao) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.itens = itens;
        this.statusPedido = statusPedido;
        this.promocao = promocao;
        this.total = calcularTotal();
        aplicarPromocao();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }

    public void aplicarPromocao() {
        if (promocao != null && promocao.validarPromocao()) {
            double desconto = promocao.aplicarPromocao(this);
            this.total -= desconto;
        }
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
        aplicarPromocao();
    }

    public Map<Produto, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Produto, Integer> itens) {
        this.itens = itens;
        this.total = calcularTotal();
        aplicarPromocao();
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

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
        aplicarPromocao();
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public int getQuantidade(Produto produto) {
        return itens.getOrDefault(produto, 0);
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
