package Negocio;

import Exceptions.PagamentoInvalidoException;
import Exceptions.DadosInvalidosException;

import static java.lang.StringTemplate.STR;

public class Caixa {
    private Pedido pedido;
    private double total;

    public Caixa(Pedido pedido) {
        this.pedido = pedido;
        this.total = 0.0;
    }

    public double calcularTotal() {
        try {
            double totalProdutos = pedido.calcularTotal();
            double valorPromocao = aplicarPromocao();
            double taxaEntrega = calcularTaxaEntrega();
            total = totalProdutos - valorPromocao + taxaEntrega;
            return total;
        } catch (DadosInvalidosException e) {
            System.out.println(STR."Erro ao calcular o total: \{e.getMessage()}");
            return 0.0;
        }
    }

    private double calcularTaxaEntrega() {
        Entrega entrega = pedido.getEntrega();
        if (entrega != null) {
            return entrega.getTaxaEntrega();
        }
        return 0.0;
    }

    private double aplicarPromocao() throws DadosInvalidosException {
        Promocao promocao = pedido.getPromocao();
        if (promocao != null) {
            return promocao.aplicarPromocao(pedido);
        }
        return 0.0;
    }

    public void processarPagamento(int galeoes, int sicles, int nuques) throws PagamentoInvalidoException {
        try {
            Pagamento pagamento = new Pagamento(1, pedido, (int) total);
            if (!pagamento.processarPagamento(galeoes, sicles, nuques)) {
                throw new PagamentoInvalidoException("Falha no pagamento.");
            }
            System.out.println("Pagamento realizado com sucesso!");
        } catch (PagamentoInvalidoException e) {
            System.out.println(e.getMessage());
        } catch (DadosInvalidosException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTotal() {
        return total;
    }
}
