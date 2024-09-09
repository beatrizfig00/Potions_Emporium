package Arquivos;

import Exceptions.DadosInvalidosException;
import Negocio.Cliente;
import java.io.*;
import java.util.ArrayList;

public class ArquivoCliente {
    private ArrayList<Cliente> clientes;
    private final String arquivoCSV;

    public ArquivoCliente(String arquivoCSV) {
        this.clientes = new ArrayList<>();
        this.arquivoCSV = arquivoCSV;
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public ArrayList<Cliente> getAllClientes() {
        return clientes;
    }

    public void removerCliente(int id) {
        clientes.removeIf(cliente -> cliente.getId() == id);
    }

    public Cliente getClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public void salvarClientes() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoCSV, false))) { // false to overwrite the file
            for (Cliente cliente : clientes) {
                writer.println(organizarLinhas(cliente));
            }
        }
    }

    public ArrayList<Cliente> carregarClientes() throws IOException, DadosInvalidosException {
        clientes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                clientes.add(criarCliente(linha));
            }
        }
        return clientes;
    }

    private static String organizarLinhas(Cliente cliente) {
        return String.format("%d,%s,%s,%s,%s",
                cliente.getId(), cliente.getNome(), cliente.getCoruja(), cliente.getFlooPowder(), cliente.getSenha());
    }

    private static Cliente criarCliente(String linha) throws DadosInvalidosException {
        String[] atributos = linha.split(",");
        if (atributos.length != 5) {
            throw new DadosInvalidosException("Dados inválidos para criar um cliente.");
        }
        return new Cliente(
                atributos[1], atributos[2], atributos[3], atributos[4]
        );
    }
}
