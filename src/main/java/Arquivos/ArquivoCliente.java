package Persistencia;

import Negocio.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoCliente {
    private static final String NOME_ARQUIVO = "clientes.csv";

    public static void salvarClientes(List<Cliente> clientes) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO)) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getId() + "," + cliente.getNome() + "," + cliente.getCoruja() + "," + cliente.getFlooPowder() + "\n");
            }
        }
    }

    public static List<Cliente> carregarClientes() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                Cliente cliente = new Cliente(partes[1], partes[2], partes[3], partes[0], new ArrayList<>()); // Simplificado
                clientes.add(cliente);
            }
        }
        return clientes;
    }
}
