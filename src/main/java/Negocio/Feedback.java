package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.FeedbackException;

public class Feedback {
    private int id;
    private Cliente cliente;
    private int nota;

    public Feedback(int id, Cliente cliente, int nota) throws DadosInvalidosException {
        if (nota < 0 || nota > 10) {
            throw new DadosInvalidosException("Nota deve estar entre 0 e 10.");
        }
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

    public void setNota(int nota) throws DadosInvalidosException, FeedbackException {
        if (nota < 0 || nota > 10) {
            throw new DadosInvalidosException("Nota deve estar entre 0 e 10.");
        }
        
        if (nota == 0) {
            throw new FeedbackException("Nota não pode ser zero.");
        }

        this.nota = nota;
    }
}
