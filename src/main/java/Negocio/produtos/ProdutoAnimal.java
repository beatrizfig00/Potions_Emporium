package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoAnimal extends Produto{
    private String habitat;

    public ProdutoAnimal(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade, String habitat) throws DadosInvalidosException {
        super(id,nome, descricao, preco, codigoBarra, quantidade);
    }

    public String getHabitat() {
        return habitat;
    }
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
    @Override
    public String toString() {
        return String.format("Animal [ID: %d, Nome: %s, Descrição: %s, Preço: R$ %.2f, Categoria: %s, Código de Barras: %s, Quantidade: %d, Habitat: %s]",
                getId(), getNome(), getDescricao(), getPreco(), getCodigoBarra(), getQuantidade(), habitat);
    }
}