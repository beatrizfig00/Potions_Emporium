package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoPocao extends Produto {
    private String efeito;
    private int tempoEfeito;

    public ProdutoPocao(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade, String efeito, int tempoEfeito) throws DadosInvalidosException {
        super(id, nome, descricao, preco, codigoBarra, quantidade);
        this.efeito = efeito;
        this.tempoEfeito = tempoEfeito;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public int getTempoEfeito() {
        return tempoEfeito;
    }

    public void setTempoEfeito(int tempoEfeito) {
        this.tempoEfeito = tempoEfeito;
    }

    @Override
    public String toString() {
        return String.format("ProdutoPocao{id=%d, nome='%s', descricao='%s', preco=%.2f, categoria='%s', codigoBarra='%s', quantidade=%d, efeito='%s', tempoEfeito=%d}",
                getId(), getNome(), getDescricao(), getPreco(), getCodigoBarra(), getQuantidade(), efeito, tempoEfeito);
    }

}
