package Negocio;

import java.util.List;
import java.util.Random;

public class Cliente {
    private int id;
    private String nome;
    private String coruja;
    private String flooPowder;
    private List<Pedido> historicoCompras;
    
    public Cliente(String nome, String coruja, String flooPowder, List<Pedido> historicoCompras) {
        this.id = gerarId(); 
        this.nome = nome;
        this.coruja = coruja;
        this.flooPowder = flooPowder;
        this.historicoCompras = historicoCompras;
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

    public List<Pedido> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Pedido> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }
}
