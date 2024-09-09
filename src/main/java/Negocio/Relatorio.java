package Negocio;

import Exceptions.RelatoriosException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relatorio {
    private List<Pedido> pedidos;
    private double totalVendas;
    private Map<Produto, Integer> produtosVendidos;

    public Relatorio(List<Pedido> pedidos) throws RelatoriosException {
        if (pedidos == null) {
            throw new RelatoriosException("Lista de pedidos não pode ser nula.");
        }
        this.pedidos = pedidos;
        this.produtosVendidos = new HashMap<>();
        this.totalVendas = calcularTotalVendas();
    }

    private double calcularTotalVendas() throws RelatoriosException {
        double total = 0.0;

        try {
            for (Pedido pedido : pedidos) {
                total += pedido.getTotal();

                for (Map.Entry<Produto, Integer> item : pedido.getItens().entrySet()) {
                    Produto produto = item.getKey();
                    int quantidade = item.getValue();

                    produtosVendidos.put(produto, produtosVendidos.getOrDefault(produto, 0) + quantidade);
                }
            }
        } catch (Exception e) {
            throw new RelatoriosException(STR."Erro ao calcular o total de vendas: \{e.getMessage()}");
        }

        return total;
    }

    public void gerarRelatorio() throws RelatoriosException {
        if (pedidos == null || pedidos.isEmpty()) {
            throw new RelatoriosException("Não há pedidos para gerar o relatório.");
        }

        try {
            System.out.println("----- Relatório de Vendas -----");

            for (Pedido pedido : pedidos) {
                System.out.println(STR."Pedido ID: \{pedido.getIdPedido()}");
                System.out.println(STR."Cliente: \{pedido.getCliente().getNome()}");
                System.out.println(STR."Status do Pedido: \{pedido.getStatusPedido()}");

                for (Map.Entry<Produto, Integer> item : pedido.getItens().entrySet()) {
                    Produto produto = item.getKey();
                    int quantidade = item.getValue();
                    System.out.println(STR."Produto: \{produto.getNome()} | Quantidade: \{quantidade} | Preço: \{produto.getPreco()}");
                }

                System.out.println(STR."Total do Pedido: \{pedido.getTotal()}");
                System.out.println("------------------------------");
            }

            System.out.println(STR."Total Geral de Vendas: \{totalVendas}");

            System.out.println("----- Produtos Vendidos -----");
            for (Map.Entry<Produto, Integer> entry : produtosVendidos.entrySet()) {
                Produto produto = entry.getKey();
                int quantidade = entry.getValue();
                System.out.println(STR."Produto: \{produto.getNome()} | Quantidade Total Vendida: \{quantidade}");
            }
            System.out.println("------------------------------");
        } catch (Exception e) {
            throw new RelatoriosException(STR."Erro ao gerar o relatório: \{e.getMessage()}");
        }
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public Map<Produto, Integer> getProdutosVendidos() {
        return produtosVendidos;
    }
}
