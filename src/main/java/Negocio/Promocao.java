package Negocio;

import Exceptions.DadosInvalidosException;

public class Promocao {
    private double percentualDesconto;

    public Promocao(double percentualDesconto) throws DadosInvalidosException {
        if (percentualDesconto < 0) {
            throw new DadosInvalidosException("Percentual de desconto não pode ser negativo.");
        }
        this.percentualDesconto = percentualDesconto;
    }

    public double calcularDesconto(double total) {
        return total * percentualDesconto / 100;
    }

    public double aplicarPromocao(Pedido pedido) {
        double total = pedido.getTotal();
        Cliente cliente = pedido.getCliente();
        double desconto = 0.0;

        if (Moeda.nuquesParaGaleoes((int) total) >= 10) {
            desconto = calcularDesconto(total);
        }

        if (cliente.getHistoricoCompras().size() >= 4) {
            double descontoAdicional = total * 0.10;
            if (descontoAdicional > desconto) {
                desconto = descontoAdicional;
            }
        }

        return desconto;
    }

    public boolean validarPromocao() {

        return percentualDesconto > 0;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(double percentualDesconto) throws DadosInvalidosException {
        if (percentualDesconto < 0) {
            throw new DadosInvalidosException("Percentual de desconto não pode ser negativo.");
        }
        this.percentualDesconto = percentualDesconto;
    }
}
