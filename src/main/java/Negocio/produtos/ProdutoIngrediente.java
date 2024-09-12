package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoIngrediente extends Produto{
    private String origem;

    public ProdutoIngrediente(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade, String origem) throws DadosInvalidosException {
        super(id,nome, descricao, preco, codigoBarra, quantidade);
        this.origem = origem;
    }

    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    @Override
    public String toString() {
        return String.format("Ingrediente [ID: %d, Nome: %s, Descrição: %s, Preço: R$ %.2f, Categoria: %s, Código de Barras: %s, Quantidade: %d, Origem: %s]",
                getId(), getNome(), getDescricao(), getPreco(), getCodigoBarra(), getQuantidade(), origem);
    }
}