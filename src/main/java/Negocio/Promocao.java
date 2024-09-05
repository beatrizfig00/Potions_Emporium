package Negocio;

public class Promocao {
    private int idPromocao;
    private String descricao;
    private double percentualDesconto;

    public Promocao(int idPromocao, String descricao, double percentualDesconto) {
        this.idPromocao = idPromocao;
        this.descricao = descricao;
        this.percentualDesconto = percentualDesconto;
    }

    public double aplicarPromocao(Pedido pedido, Cliente cliente) {
        double desconto5Porcento = 0.0;
        double desconto10Porcento = 0.0;

        if (Moeda.nuquesParaGaleoes((int) pedido.getTotal()) >= 100) {
            desconto5Porcento = pedido.getTotal() * 0.05;
        }

        if (cliente.getHistoricoCompras().size() >= 3) {
            desconto10Porcento = pedido.getTotal() * 0.10;
        }

        if (desconto10Porcento > desconto5Porcento) {
            return pedido.getTotal() - desconto10Porcento;
        } else {
            return pedido.getTotal() - desconto5Porcento;
        }
    }

    public boolean validarPromocao() {
        return percentualDesconto > 0;
    }

    // Getters e Setters
    public int getIdPromocao() {
        return idPromocao;
    }

    public void setIdPromocao(int idPromocao) {
        this.idPromocao = idPromocao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }
}
