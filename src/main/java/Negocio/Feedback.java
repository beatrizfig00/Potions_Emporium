package Negocio;

public class Feedback {
    private int id;
    private Cliente cliente;
    private int nota; 

    public Feedback(int id, Cliente cliente, int nota) {
        this.id = id;
        this.cliente = cliente;
        this.nota = nota;
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
