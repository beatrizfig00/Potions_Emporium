package Negocio;

import Exceptions.PagamentoInvalidoException;
import Exceptions.DadosInvalidosException;

public class Pagamento {

    private int idPagamento;
    private Pedido pedido;
    private int valor;
    private boolean pagamentoProcessado;

    public Pagamento(int idPagamento, Pedido pedido, int valor) throws DadosInvalidosException, PagamentoInvalidoException {
        if (pedido == null) {
            throw new DadosInvalidosException("Pedido não pode ser nulo.");
        }
        if (valor <= 0) {
            throw new PagamentoInvalidoException("Valor do pagamento deve ser positivo.");
        }

        this.idPagamento = idPagamento;
        this.pedido = pedido;
        this.valor = valor;
        this.pagamentoProcessado = false;
    }

    public boolean processarPagamento(int galeoes, int sicles, int nuques) {
        int totalNuquesRecebidos = Moeda.galeoesParaNuques(galeoes)
                + Moeda.siclesParaNuques(sicles)
                + nuques;

        if (totalNuquesRecebidos >= valor) {
            pagamentoProcessado = true;
            int troco = totalNuquesRecebidos - valor;
            distribuirTroco(troco);
            return true;
        } else {
     //       System.out.println(STR."Pagamento insuficiente. Faltam \{valor - totalNuquesRecebidos} Nuques.");
            return false;
        }
    }

    public boolean validarPagamento() {
        return pagamentoProcessado;
    }

    private void distribuirTroco(int troco) {
        int galeoes = Moeda.nuquesParaGaleoes(troco);
        int trocoRestante = troco - Moeda.galeoesParaNuques(galeoes);

        int sicles = Moeda.nuquesParaSicles(trocoRestante);
        trocoRestante -= Moeda.siclesParaNuques(sicles);

        int nuques = trocoRestante;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) throws DadosInvalidosException {
        if (pedido == null) {
            throw new DadosInvalidosException("Pedido não pode ser nulo.");
        }
        this.pedido = pedido;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) throws PagamentoInvalidoException {
        if (valor <= 0) {
            throw new PagamentoInvalidoException("Valor do pagamento deve ser positivo.");
        }
        this.valor = valor;
    }

    public boolean isPagamentoProcessado() {
        return pagamentoProcessado;
    }

}