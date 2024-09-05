package Negocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relatorio {
    private List<Pedido> pedidos;
    private double totalVendas;
    private Map<Produto, Integer> produtosVendidos;

    public Relatorio(List<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.produtosVendidos = new HashMap<>();
        this.totalVendas = calcularTotalVendas();
    }

    private double calcularTotalVendas() {
        double total = 0.0;
        
        for (Pedido pedido : pedidos) {
            total += pedido.getTotal();

            for (Map.Entry<Produto, Integer> item : pedido.getItens().entrySet()) {
                Produto produto = item.getKey();
                int quantidade = item.getValue();

                produtosVendidos.put(produto, produtosVendidos.getOrDefault(produto, 0) + quantidade);
            }
        }
        
        return total;
    }

    public void gerarRelatorio() {
        System.out.println("----- Relatório de Vendas -----");

        for (Pedido pedido : pedidos) {
            System.out.println("Pedido ID: " + pedido.getIdPedido());
            System.out.println("Cliente: " + pedido.getCliente().getNome());
            System.out.println("Status do Pedido: " + pedido.getStatusPedido());

            for (Map.Entry<Produto, Integer> item : pedido.getItens().entrySet()) {
                Produto produto = item.getKey();
                int quantidade = item.getValue();
                System.out.println("Produto: " + produto.getNome() + " | Quantidade: " + quantidade + " | Preço: " + produto.getPreco());
            }

            System.out.println("Total do Pedido: " + pedido.getTotal());
            System.out.println("------------------------------");
        }

        System.out.println("Total Geral de Vendas: " + totalVendas);

        System.out.println("----- Produtos Vendidos -----");
        for (Map.Entry<Produto, Integer> entry : produtosVendidos.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            System.out.println("Produto: " + produto.getNome() + " | Quantidade Total Vendida: " + quantidade);
        }
        System.out.println("------------------------------");
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public Map<Produto, Integer> getProdutosVendidos() {
        return produtosVendidos;
    }
}
