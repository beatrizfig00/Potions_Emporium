package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoAnimal extends Produto{
    private String habitat;

    public ProdutoAnimal(int id, String nome, String descricao, double preco, String categoria, String codigoBarra,int quantidade, String habitat) throws DadosInvalidosException {
        super(id,nome, descricao, preco, categoria, codigoBarra, quantidade);
        this.habitat = this.habitat;
    }

    public String getHabitat() {
        return habitat;
    }
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
}