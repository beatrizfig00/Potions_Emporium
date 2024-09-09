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
    private List<Feedback> feedbacks;  // Nova lista para armazenar feedbacks

    public Cliente(String nome, String coruja, String flooPowder, String senha, List<Pedido> historicoCompras) throws DadosInvalidosException {
        this.id = gerarId();
        this.nome = nome;
        this.coruja = coruja;
        this.flooPowder = flooPowder;
        this.senha = senha;
        this.historicoCompras = historicoCompras;
        this.feedbacks = new ArrayList<>();  // Inicializando a lista de feedbacks vazia
    }

    // Método para gerar um ID aleatório
    private int gerarId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    // Getters e setters normais para os atributos existentes
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

    // Métodos para lidar com feedbacks
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void adicionarFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }
}
