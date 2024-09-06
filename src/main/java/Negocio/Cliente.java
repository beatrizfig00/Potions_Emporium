package Negocio;

import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private String coruja;
    private String flooPowder;
    private List<Pedido> historicoCompras;

    public Cliente(int id, String nome, String coruja, String flooPowder, List<Pedido> historicoCompras) {
        this.id = id;
        this.nome = nome;
        this.coruja = coruja;
        this.flooPowder = flooPowder;
        this.historicoCompras = historicoCompras;
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

    public List<Pedido> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Pedido> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }
}
