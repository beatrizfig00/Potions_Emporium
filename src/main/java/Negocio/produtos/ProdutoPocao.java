package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoPocao extends Produto {
    private String efeito;
    private int tempoefeito;

    public ProdutoPocao(int id, String nome, String descricao, double preco, String categoria, String codigoBarra, int quantidade, String efeito, int tempoefeito) throws DadosInvalidosException {
        super(id,nome, descricao, preco, categoria, codigoBarra, quantidade);
        this.efeito = efeito;
        this.tempoefeito = tempoefeito;
    }

    public String getEfeito() {
        return efeito;
    }
    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public int getTempoefeito() {
        return tempoefeito;
    }

    public void setTempoefeito(int tempoefeito) {
        this.tempoefeito = tempoefeito;
    }
}