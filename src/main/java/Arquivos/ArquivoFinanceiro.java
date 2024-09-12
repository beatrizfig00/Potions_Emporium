package Arquivos;

import Negocio.Pagamento;
import Negocio.Relatorio;
import Negocio.Pedido; 
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

    public void carregarPagamentos(Map<Integer, Pedido> pedidos) throws IOException, FormatoArquivoException {
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

                Pedido pedido = pedidos.get(idPagamento);
                if (pedido == null) {
                    throw new DadosInvalidosException("Pedido não encontrado para o pagamento: " + idPagamento);
                }

                Pagamento pagamento = new Pagamento(idPagamento, pedido, valor);
                if (pago) {
                  //  pagamento.processarPagamento(pedido.getTotal(), 0, 0);
                }
                pagamentos.put(idPagamento, pagamento);
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de pagamentos não encontrado.");
        } catch (PagamentoInvalidoException e) {
            throw new RuntimeException("Erro ao processar o pagamento: " + e.getMessage(), e);
        } catch (DadosInvalidosException e) {
            throw new RuntimeException("Erro ao carregar o pagamento: " + e.getMessage(), e);
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
            int linhaAtual = 0;
            while ((linha = reader.readLine()) != null) {
                linhaAtual++;
                if (!validarFormatoRelatorio(linha)) { 
                    throw new FormatoArquivoException("Erro no formato do relatório na linha: " + linhaAtual);
                }
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

    private boolean validarFormatoRelatorio(String linha) {
        return linha != null && !linha.trim().isEmpty();
    }
}
