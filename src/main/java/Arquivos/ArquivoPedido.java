package Persistencia;

import Negocio.Pedido;
import Negocio.Produto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArquivoPedidos {
    private static final String NOME_ARQUIVO = "pedidos.csv";

    public static void salvarPedidos(List<Pedido> pedidos) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO)) {
            for (Pedido pedido : pedidos) {
                writer.write(pedido.getIdPedido() + "," + pedido.getCliente().getNome() + "," + pedido.getTotal() + "\n");
            }
        }
    }

    public static List<Pedido> carregarPedidos() throws IOException {
        List<Pedido> pedidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Simulação de leitura do pedido. Ajustar de acordo com a estrutura do Pedido.
            }
        }
        return pedidos;
    }
}
