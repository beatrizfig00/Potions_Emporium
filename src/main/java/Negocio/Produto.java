package Negocio;

import Exceptions.DadosInvalidosException;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private String categoria;
    private String codigoBarra;
    private int quantidade;

    public Produto(int id, String nome, String descricao, double preco, String categoria, String codigoBarra, int quantidade) throws DadosInvalidosException {
        if (preco < 0) {
            throw new DadosInvalidosException("O preço do produto não pode ser negativo.");
        }
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade do produto não pode ser negativa.");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.codigoBarra = codigoBarra;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) throws DadosInvalidosException {
        if (preco < 0) {
            throw new DadosInvalidosException("O preço do produto não pode ser negativo.");
        }
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }
    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) throws DadosInvalidosException {
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade do produto não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }
}