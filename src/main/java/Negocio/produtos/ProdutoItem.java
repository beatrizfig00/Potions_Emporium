package Negocio.produtos;

import Negocio.Produto;

public class ProdutoItem extends Produto{
    private String poder;

    public ProdutoItem(int id, String nome, String descricao, double preco, String categoria, String codigoBarra, int quantidade, String poder) {
        super(id, nome, descricao, preco, categoria, codigoBarra, quantidade);
        this.poder = poder;
    }

    public String getPoder() {
        return poder;
    }
    public void setPoder(String poder) {
        this.poder = poder;
    }
}