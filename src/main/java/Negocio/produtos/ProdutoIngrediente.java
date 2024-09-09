package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoIngrediente extends Produto{
    private String origem;

    public ProdutoIngrediente(int id, String nome, String descricao, double preco, String categoria, String codigoBarra, int quantidade, String origem) throws DadosInvalidosException {
        super(id,nome, descricao, preco, categoria, codigoBarra, quantidade);
        this.origem = origem;
    }

    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
}