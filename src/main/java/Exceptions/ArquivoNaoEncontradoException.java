package Exceptions;

public class ArquivoNaoEncontradoException extends IOException {
    public ArquivoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
