package Arquivos;

import Exceptions.DadosInvalidosException;
import Exceptions.PagamentoInvalidoException;
import Negocio.Pagamento;
import Negocio.Pedido;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArquivoFinanceiro {

    private Map<Integer, Pagamento> pagamentos;
    private final String arquivoPagamentos;
    private final String arquivoRelatorio;

    public ArquivoFinanceiro(String arquivoPagamentos, String arquivoRelatorio) {
        this.arquivoPagamentos = arquivoPagamentos;
        this.arquivoRelatorio = arquivoRelatorio;
        this.pagamentos = new HashMap<>();

        File arquivoPag = new File(arquivoPagamentos);
        if (!arquivoPag.exists()) {
            try {
                arquivoPag.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo de pagamentos: " + e.getMessage());
            }
        }

        try {
            carregarPagamentos();
        } catch (IOException | DadosInvalidosException | PagamentoInvalidoException e) {
            System.err.println("Erro ao carregar os pagamentos: " + e.getMessage());
        }
    }

    public void carregarPagamentos() throws IOException, DadosInvalidosException, PagamentoInvalidoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoPagamentos))) {
            String linha;
            reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (dados.length >= 4) {
                    try {
                        int idPagamento = Integer.parseInt(dados[0].trim());
                        int idPedido = Integer.parseInt(dados[1].trim());
                        double valor = Double.parseDouble(dados[2].trim());
                        boolean pagamentoProcessado = Boolean.parseBoolean(dados[3].trim());

                        // Verificar se o pagamento é válido
                        if (valor < 0) {
                            throw new PagamentoInvalidoException("Pagamento com valor inválido: " + valor);
                        }

                        Pagamento pagamento = new Pagamento(idPagamento, idPedido, valor, pagamentoProcessado);
                        pagamentos.put(idPagamento, pagamento);
                    } catch (NumberFormatException | PagamentoInvalidoException e) {
                        System.err.println("Erro ao processar linha: " + linha);
                        throw new PagamentoInvalidoException("Erro ao processar pagamento: " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha com dados insuficientes: " + linha);
                }
            }
        }
    }


    public void salvarRelatorio(Map<Integer, Pedido> pedidos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoRelatorio))) {
            writer.println("Relatório Financeiro:");
            writer.println("---------------------");

            double totalRecebido = 0;
            double totalDevido = 0;

            for (Pedido pedido : pedidos.values()) {
                Pagamento pagamento = pagamentos.get(pedido.getId());
                if (pagamento != null && pagamento.isPagamentoProcessado()) {
                    totalRecebido += pagamento.getValor();
                } else {
                    totalDevido += pedido.calcularTotal();
                }
            }

            writer.printf("Total Recebido: %.2f nuques%n", totalRecebido);
            writer.printf("Total Devido: %.2f nuques%n", totalDevido);
            writer.printf("Saldo Final: %.2f nuques%n", (totalRecebido - totalDevido));
        }
    }

    // Método principal
    public static void main(String[] args) {
        Map<Integer, Pedido> pedidos = new HashMap<>();
        ArquivoFinanceiro arquivoFinanceiro = new ArquivoFinanceiro("pagamentos.txt", "relatorio_financeiro.txt");

        // Carregar pagamentos
        try {
            arquivoFinanceiro.carregarPagamentos();
        } catch (IOException | DadosInvalidosException | PagamentoInvalidoException e) {
            System.out.println("Erro ao carregar pagamentos: " + e.getMessage());
        }

        // Gerar relatório financeiro
        try {
            arquivoFinanceiro.salvarRelatorio(pedidos);
            System.out.println("Relatório financeiro salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar relatório financeiro: " + e.getMessage());
        }
    }
}
