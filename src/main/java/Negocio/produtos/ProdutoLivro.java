package Negocio.produtos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;

public class ProdutoLivro extends Produto{
    private String autor;
    private int numeroPaginas;

    public ProdutoLivro(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade, String autor, int numeroPaginas) throws DadosInvalidosException {
        super(id,nome, descricao, preco, codigoBarra, quantidade);
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
    @Override
    public String toString() {
        return String.format("Livro [ID: %d, Nome: %s, Descrição: %s, Preço: R$ %.2f, Categoria: %s, Código de Barras: %s, Quantidade: %d, Autor: %s, Páginas: %d]",
                getId(), getNome(), getDescricao(), getPreco(), getCodigoBarra(), getQuantidade(), autor, numeroPaginas);
    }
}