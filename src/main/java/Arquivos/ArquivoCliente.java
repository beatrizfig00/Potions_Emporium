package Arquivos;

import Exceptions.DadosInvalidosException;
import Exceptions.PromoInvalidaException;
import Negocio.Cliente;
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
            }
        }
    }

    public List<Cliente> carregarClientes() throws IOException, FormatoArquivoException, DadosInvalidosException, PromoInvalidaException {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(ARQUIVO_CLIENTES);
        if (!file.exists()) {
            throw new ArquivoNaoEncontradoException("Arquivo de clientes não encontrado.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length < 5) {
                    throw new FormatoArquivoException("Formato inválido no arquivo de clientes.");
                }

                List<Pedido> historicoCompras = new ArrayList<>();
                while ((linha = reader.readLine()) != null && linha.startsWith("Pedido:")) {
                    String[] partesPedido = linha.substring(7).split(",");
                    if (partesPedido.length != 3) {
                        throw new FormatoArquivoException("Formato inválido no histórico de compras.");
                    }
                    Pedido pedido = new Pedido(Integer.parseInt(partesPedido[0]), null, null, partesPedido[2], null);
                    pedido.setTotal(Double.parseDouble(partesPedido[1]));
                    historicoCompras.add(pedido);
                }

                Cliente cliente = new Cliente(partes[1], partes[2], partes[3], partes[4], historicoCompras);
                clientes.add(cliente);
            }
        }
        return clientes;
    }
}
