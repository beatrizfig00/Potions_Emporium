package Persistencia;

import Negocio.Produto;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArquivoEstoque {
    private static final String NOME_ARQUIVO = "estoque.csv";

    public static void salvarEstoque(Map<Produto, Integer> estoque) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO)) {
            for (Map.Entry<Produto, Integer> entry : estoque.entrySet()) {
                Produto produto = entry.getKey();
                writer.write(produto.getId() + "," + produto.getNome() + "," + produto.getPreco() + "," + entry.getValue() + "\n");
            }
        }
    }

    public static Map<Produto, Integer> carregarEstoque() throws IOException {
        Map<Produto, Integer> estoque = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                Produto produto = new Produto(Integer.parseInt(partes[0]), partes[1], "", Double.parseDouble(partes[2]), "", "", 0); // Simplificado
                estoque.put(produto, Integer.parseInt(partes[3]));
            }
        }
        return estoque;
    }
}
