package Negocio;

import Exceptions.DadosInvalidosException;

public class Promocao {
    private double percentualDesconto;
    private int tipo; // Tipo de promoção (por exemplo, 1 para desconto percentual, 2 para desconto fixo)

    // Construtor para desconto percentual
    public Promocao(double percentualDesconto) {
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 100.");
        }
        this.percentualDesconto = percentualDesconto;
        this.tipo = 1; // Tipo 1 para percentual
    }

    // Construtor para desconto fixo
    public Promocao(double valorDesconto, boolean isFixo) {
        if (valorDesconto < 0) {
            throw new IllegalArgumentException("Valor de desconto não pode ser negativo.");
        }
        this.percentualDesconto = valorDesconto;
        this.tipo = isFixo ? 2 : 1; // Tipo 2 para fixo, 1 para percentual
    }

    public boolean validarPromocao() {
        // Valida se o desconto é válido para o tipo de promoção
        if (tipo == 1) {
            return percentualDesconto >= 0 && percentualDesconto <= 100;
        } else if (tipo == 2) {
            return percentualDesconto >= 0;
        }
        return false;
    }

    public double aplicarPromocao(Pedido pedido) throws DadosInvalidosException {
        if (!validarPromocao()) {
            throw new DadosInvalidosException("Promoção inválida.");
        }

        double total = pedido.calcularTotal();
        if (tipo == 1) {
            // Aplicar desconto percentual
            return (percentualDesconto / 100) * total;
        } else if (tipo == 2) {
            // Aplicar desconto fixo
            return Math.min(percentualDesconto, total); // O desconto fixo não pode ser maior que o total
        }
        return 0.0;
    }
}
