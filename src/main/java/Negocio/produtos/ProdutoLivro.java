package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoLivro extends Produto{
    private String autor;
    private int numeroPaginas;

    public ProdutoLivro(int id, String nome, String descricao, double preco, String categoria, String codigoBarra,int quantidade, String autor, int numeroPaginas) throws DadosInvalidosException {
        super(id,nome, descricao, preco, categoria, codigoBarra, quantidade);
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
}