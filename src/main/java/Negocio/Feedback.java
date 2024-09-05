package Negocio;

import java.util.Date;

public class Feedback {
    private int id;
    private Cliente cliente;
    private String mensagem;
    private Date data;

    public Feedback(int id, Cliente cliente, String mensagem, Date data) {
        this.id = id;
        this.cliente = cliente;
        this.mensagem = mensagem;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void registrarFeedback() {
            }

    public void analisarFeedback() {
    }
}
