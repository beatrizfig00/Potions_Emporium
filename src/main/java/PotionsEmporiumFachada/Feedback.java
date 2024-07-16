public class FeedbackCliente {
    private String comentario;
    private int avaliacao;
    private int data;
  
    public FeedbackCliente(String comentario, int avaliacao, int data) {
        this.comentario = comentario;
        this.avaliacao = avaliacao;
        this.data = data;
    }
  
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
