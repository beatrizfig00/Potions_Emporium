package Negocio;

public class Pagamento {

    private int idPagamento;
    private Pedido pedido;
    private int valor;
    private boolean pagamentoProcessado;

    public Pagamento(int idPagamento, Pedido pedido, int valor) {
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
            System.out.println("Pagamento insuficiente. Faltam "
                    + (valor - totalNuquesRecebidos) + " Nuques.");
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

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isPagamentoProcessado() {
        return pagamentoProcessado;
    }
    
}