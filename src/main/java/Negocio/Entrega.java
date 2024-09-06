package Negocio;

public class Entrega {
    private int idEntrega;
    private Pedido pedido;
    private String tipoEntrega;
    private int taxaEntrega;
    private Endereco enderecoEntrega;

    public Entrega(int idEntrega, Pedido pedido, String tipoEntrega, Endereco enderecoEntrega) {
        this.idEntrega = idEntrega;
        this.pedido = pedido;
        this.tipoEntrega = tipoEntrega;
        this.enderecoEntrega = enderecoEntrega;
        this.taxaEntrega = calcularTaxaEntrega();
    }

    public int calcularTaxaEntrega() {
        int taxaSicles;

        if (tipoEntrega.equalsIgnoreCase("teletransporte")) {
            taxaSicles = 10;
        } else if (tipoEntrega.equalsIgnoreCase("coruja")) {
            taxaSicles = 5;
        } else {
            taxaSicles = 0;
        }

        return Moeda.siclesParaNuques(taxaSicles);
    }

    public void selecionarEnderecoEntrega(Endereco novoEndereco) {
        this.enderecoEntrega = novoEndereco;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
        this.taxaEntrega = calcularTaxaEntrega();
    }

    public int getTaxaEntrega() {
        return taxaEntrega;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}