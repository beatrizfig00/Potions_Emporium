package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;
import Exceptions.PromoInvalidaException;

import java.util.Map;

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private Map<Produto, Integer> itens;
    private double total;
    private String statusPedido;
    private Promocao promocao;
    private Entrega entrega;

    public Pedido(int idPedido, Cliente cliente, Map<Produto, Integer> itens, String statusPedido, Promocao promocao)
            throws DadosInvalidosException, PromoInvalidaException {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo.");
        }
        if (itens == null || itens.isEmpty()) {
            throw new DadosInvalidosException("Itens não podem ser nulos ou vazios.");
        }
        if (statusPedido == null || statusPedido.isEmpty()) {
            throw new DadosInvalidosException("Status do pedido não pode ser nulo ou vazio.");
        }

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

    public void aplicarPromocao() throws PromoInvalidaException {
        if (promocao != null) {
            if (!promocao.validarPromocao()) {
                throw new PromoInvalidaException("Promoção inválida.");
            }
            double desconto = promocao.aplicarPromocao(this);
            this.total -= desconto;
        }
    }

    public void atualizarStatus(String novoStatus) throws DadosInvalidosException {
        if (novoStatus == null || novoStatus.isEmpty()) {
            throw new DadosInvalidosException("Novo status não pode ser nulo ou vazio.");
        }
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

    public void setCliente(Cliente cliente) throws DadosInvalidosException, PromoInvalidaException {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
        aplicarPromocao();
    }

    public Map<Produto, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Produto, Integer> itens) throws DadosInvalidosException, PromoInvalidaException {
        if (itens == null || itens.isEmpty()) {
            throw new DadosInvalidosException("Itens não podem ser nulos ou vazios.");
        }
        this.itens = itens;
        this.total = calcularTotal();
        aplicarPromocao();
    }

    public int getTotal() {
        return (int) total;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) throws DadosInvalidosException {
        if (statusPedido == null || statusPedido.isEmpty()) {
            throw new DadosInvalidosException("Status do pedido não pode ser nulo ou vazio.");
        }
        this.statusPedido = statusPedido;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) throws PromoInvalidaException {
        if (promocao != null && !promocao.validarPromocao()) {
            throw new PromoInvalidaException("Promoção inválida.");
        }
        this.promocao = promocao;
        aplicarPromocao();
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public int getQuantidade(Produto produto) throws ItemNaoEncontradoException {
        if (!itens.containsKey(produto)) {
            throw new ItemNaoEncontradoException("Produto não encontrado no pedido.");
        }
        return itens.get(produto);
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
