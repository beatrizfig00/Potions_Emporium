package Negocio;

public class Caixa {
    private Pedido pedido;
    private double total;

    public Caixa(Pedido pedido) {
        this.pedido = pedido;
        this.total = 0.0;
    }

    public double calcularTotal() {
        double totalProdutos = pedido.calcularTotal();
        double valorPromocao = aplicarPromocao();
        double taxaEntrega = calcularTaxaEntrega();
        total = totalProdutos - valorPromocao + taxaEntrega;
        return total;
    }

    private double calcularTaxaEntrega() {
        Entrega entrega = pedido.getEntrega();
        if (entrega != null) {
            return entrega.getTaxaEntrega();
        }
        return 0.0;
    }

    private double aplicarPromocao() {
        Promocao promocao = pedido.getPromocao();
        Cliente cliente = pedido.getCliente();
        if (promocao != null && cliente != null) {
            return promocao.aplicarPromocao(pedido, cliente);
        }
        return 0.0;
    }

    public void processarPagamento(int galeoes, int sicles, int nuques) {
        Pagamento pagamento = new Pagamento(1, pedido, (int) total); 
        if (pagamento.processarPagamento(galeoes, sicles, nuques)) {
            System.out.println("Pagamento realizado com sucesso!");
        } else {
            System.out.println("Falha no pagamento.");
        }
    }

    public double getTotal() {
        return total;
    }
}
