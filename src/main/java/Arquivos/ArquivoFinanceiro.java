package Arquivos;

import Negocio.Pagamento;
import Negocio.Relatorio;
import Exceptions.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArquivoFinanceiro {
    private final String arquivoPagamentos;
    private final String arquivoRelatorios;

    public ArquivoFinanceiro(String arquivoPagamentos, String arquivoRelatorios) {
        this.arquivoPagamentos = arquivoPagamentos;
        this.arquivoRelatorios = arquivoRelatorios;
    }

    public void salvarPagamento(Pagamento pagamento) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoPagamentos, true))) {
            writer.println(organizarLinhaPagamento(pagamento));
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Erro ao salvar o pagamento: arquivo não encontrado.");
        }
    }

    public void carregarPagamentos() throws IOException, FormatoArquivoException {
        Map<Integer, Pagamento> pagamentos = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoPagamentos))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    throw new FormatoArquivoException("Erro no formato do arquivo de pagamentos.");
                }

                int idPagamento = Integer.parseInt(partes[0]);
                int valor = Integer.parseInt(partes[1]);
                boolean pago = Boolean.parseBoolean(partes[2]);

                Pagamento pagamento = new Pagamento(idPagamento, null, valor);
                if (pago) {
                    pagamento.processarPagamento(0, 0, 0);
                }
                pagamentos.put(idPagamento, pagamento);
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de pagamentos não encontrado.");
        } catch (PagamentoInvalidoException e) {
            throw new RuntimeException(e);
        } catch (DadosInvalidosException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvarRelatorio(Relatorio relatorio) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoRelatorios, false))) {
            writer.println("Total de Vendas: " + relatorio.getTotalVendas());
            writer.println("Produtos Vendidos:");
            relatorio.getProdutosVendidos().forEach((produto, quantidade) -> {
                writer.println(produto.getNome() + " - Quantidade: " + quantidade);
            });
        } catch (IOException e) {
            throw new RelatoriosException("Erro ao salvar o relatório financeiro.");
        }
    }

    public void carregarRelatorio() throws IOException, FormatoArquivoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoRelatorios))) {
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

    private static String organizarLinhaPagamento(Pagamento pagamento) {
        return String.format("%d,%d,%b",
                pagamento.getIdPagamento(), pagamento.getValor(), pagamento.validarPagamento());
    }
}