package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoItem extends Produto{
    private String poder;

    public ProdutoItem(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade, String poder) throws DadosInvalidosException {
        super(id, nome, descricao, preco, codigoBarra, quantidade);
        this.poder = poder;
    }

    public String getPoder() {
        return poder;
    }
    public void setPoder(String poder) {
        this.poder = poder;
    }
    @Override
    public String toString() {
        return String.format("Item [ID: %d, Nome: %s, Descrição: %s, Preço: R$ %.2f, Categoria: %s, Código de Barras: %s, Quantidade: %d, Poder: %s]",
                getId(), getNome(), getDescricao(), getPreco(), getCodigoBarra(), getQuantidade(), poder);
    }
}