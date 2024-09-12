package Negocio;

import Exceptions.DadosInvalidosException;
import java.util.Objects;
import java.util.Random;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private String codigoBarra;
    private int quantidade;

    public Produto(int id, String nome, String descricao, double preco, String codigoBarra, int quantidade) throws DadosInvalidosException {
        if (preco < 0) {
            throw new DadosInvalidosException("O preço do produto não pode ser negativo.");
        }
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade do produto não pode ser negativa.");
        }
        this.id = gerarId();
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.codigoBarra = codigoBarra;
        this.quantidade = quantidade;
    }
    private int gerarId() {
        Random random = new Random();
        return random.nextInt(1000000);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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