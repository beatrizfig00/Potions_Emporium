package Arquivos;

import Exceptions.DadosInvalidosException;
import Exceptions.FeedbackException;
import Exceptions.PromoInvalidaException;
import Negocio.Cliente;
import Negocio.Feedback;
import Negocio.Pedido;
import Exceptions.ArquivoNaoEncontradoException;
import Exceptions.FormatoArquivoException;

import java.io.*;
import java.util.*;

public class ArquivoClientes {

    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public void salvarClientes(List<Cliente> clientes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES))) {
            for (Cliente cliente : clientes) {
                writer.write(STR."\{cliente.getId()},\{cliente.getNome()},\{cliente.getCoruja()},\{cliente.getFlooPowder()},\{cliente.getSenha()}");
                writer.newLine();

                for (Pedido pedido : cliente.getHistoricoCompras()) {
                    writer.write(STR."Pedido:\{pedido.getIdPedido()},\{pedido.getTotal()},\{pedido.getStatusPedido()}");
                    writer.newLine();
                }

                for (Feedback feedback : cliente.getFeedbacks()) {
                    writer.write(STR."Feedback:\{feedback.getId()},\{feedback.getNota()}");
                    writer.newLine();
                }
            }
        }
    }

    public List<Cliente> carregarClientes() throws IOException, FormatoArquivoException, DadosInvalidosException, FeedbackException, PromoInvalidaException {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(ARQUIVO_CLIENTES);
        if (!file.exists()) {
            throw new ArquivoNaoEncontradoException("Arquivo de clientes não encontrado.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            Cliente clienteAtual = null;
            List<Pedido> historicoCompras = new ArrayList<>();
            List<Feedback> feedbacks = new ArrayList<>();

            while ((linha = reader.readLine()) != null) {
                if (!linha.startsWith("Pedido:") && !linha.startsWith("Feedback:")) {
                    String[] partes = linha.split(",");
                    if (partes.length < 5) {
                        throw new FormatoArquivoException("Formato inválido no arquivo de clientes.");
                    }

                    if (clienteAtual != null) {
                        clienteAtual.setHistoricoCompras(historicoCompras);
                        clienteAtual.setFeedbacks(feedbacks);
                        clientes.add(clienteAtual);
                    }

                    clienteAtual = new Cliente(partes[1], partes[2], partes[3], partes[4], historicoCompras);
                    historicoCompras = new ArrayList<>();
                    feedbacks = new ArrayList<>();
                } else if (linha.startsWith("Pedido:")) {
                    String[] partesPedido = linha.substring(7).split(",");
                    if (partesPedido.length != 3) {
                        throw new FormatoArquivoException("Formato inválido no histórico de compras.");
                    }
                    Pedido pedido = new Pedido(Integer.parseInt(partesPedido[0]), null, null, partesPedido[2], null);
                    pedido.setTotal(Double.parseDouble(partesPedido[1]));
                    historicoCompras.add(pedido);
                } else if (linha.startsWith("Feedback:")) {
                    String[] partesFeedback = linha.substring(9).split(",");
                    if (partesFeedback.length != 2) {
                        throw new FormatoArquivoException("Formato inválido no feedback.");
                    }
                    int idFeedback = Integer.parseInt(partesFeedback[0]);
                    int nota = Integer.parseInt(partesFeedback[1]);

                    Feedback feedback = new Feedback(idFeedback, clienteAtual, nota);
                    feedbacks.add(feedback);
                }
            }

            if (clienteAtual != null) {
                clienteAtual.setHistoricoCompras(historicoCompras);
                clienteAtual.setFeedbacks(feedbacks);
                clientes.add(clienteAtual);
            }
        }
        return clientes;
    }
}
