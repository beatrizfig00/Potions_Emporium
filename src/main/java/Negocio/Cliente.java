package Negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Exceptions.DadosInvalidosException;

public class Cliente {
    private int id;
    private String nome;
    private String coruja;
    private String flooPowder;
    private String senha;
    private List<Pedido> historicoCompras;
    private List<Feedback> feedbacks;

    public Cliente(String nome, String coruja, String flooPowder, String senha) throws DadosInvalidosException {
        this.id = gerarId();
        this.nome = nome;
        this.coruja = coruja;
        this.flooPowder = flooPowder;
        this.senha = senha;
        this.historicoCompras = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public Cliente(int id, String nome, String coruja, String flooPowder, String senha) throws DadosInvalidosException {
        this.id = id;
        this.nome = nome;
        this.coruja = coruja;
        this.flooPowder = flooPowder;
        this.senha = senha;
        this.historicoCompras = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    private int gerarId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoruja() {
        return coruja;
    }

    public void setCoruja(String coruja) {
        this.coruja = coruja;
    }

    public String getFlooPowder() {
        return flooPowder;
    }

    public void setFlooPowder(String flooPowder) {
        this.flooPowder = flooPowder;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pedido> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Pedido> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void adicionarFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }

    public void adicionarPedido(Pedido pedido) throws DadosInvalidosException {
        if (pedido == null) {
            throw new DadosInvalidosException("Pedido não pode ser nulo.");
        }
        this.historicoCompras.add(pedido);
    }
}
