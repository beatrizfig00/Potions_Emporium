package Persistencia;

import Negocio.Pagamento;
import Negocio.Relatorio;
import java.io.*;
import Exceptions.*;

public class ArquivoFinanceiro {
    private static final String NOME_ARQUIVO_PAGAMENTOS = "pagamentos.csv";
    private static final String NOME_ARQUIVO_RELATORIO = "relatorio.csv";

    public static void salvarPagamento(Pagamento pagamento) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO_PAGAMENTOS, true)) {
            writer.write(pagamento.getIdPagamento() + "," + pagamento.getValor() + "," + pagamento.validarPagamento() + "\n");
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Erro ao salvar o pagamento: arquivo não encontrado.");
        }
    }

    public static void carregarPagamentos() throws IOException, FormatoArquivoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO_PAGAMENTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    throw new FormatoArquivoException("Erro no formato do arquivo de pagamentos.");
                }
      
                int idPagamento = Integer.parseInt(partes[0]);
                double valor = Double.parseDouble(partes[1]);
                boolean pago = Boolean.parseBoolean(partes[2]);
                System.out.println("ID: " + idPagamento + " | Valor: " + valor + " | Pago: " + pago);
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de pagamentos não encontrado.");
        }
    }

    public static void salvarRelatorio(Relatorio relatorio) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO_RELATORIO)) {
            writer.write("Total de Vendas: " + relatorio.getTotalVendas() + "\n");
            writer.write("Produtos Vendidos:\n");
            relatorio.getProdutosVendidos().forEach((produto, quantidade) -> {
                try {
                    writer.write(produto.getNome() + " - Quantidade: " + quantidade + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RelatoriosException("Erro ao salvar o relatório financeiro.");
        }
    }

    public static void carregarRelatorio() throws IOException, FormatoArquivoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO_RELATORIO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de relatórios não encontrado.");
        } catch (IOException e) {
            throw new FormatoArquivoException("Erro no formato do arquivo de relatórios.");
        }
    }
}
