package Arquivos;

import Exceptions.PagamentoInvalidoException;
import Negocio.Pagamento;
import Negocio.Pedido;
import Exceptions.ArquivoNaoEncontradoException;
import Exceptions.FormatoArquivoException;
import Exceptions.DadosInvalidosException;
import java.io.*;
import java.util.*;

public class ArquivoFinanceiro {

    private static final String ARQUIVO_FINANCEIRO = "financeiro.txt";

    public void salvarPagamentos(List<Pagamento> pagamentos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_FINANCEIRO))) {
            for (Pagamento pagamento : pagamentos) {
                writer.write(STR."\{pagamento.getIdPagamento()},\{pagamento.getPedido().getIdPedido()},\{pagamento.getValor()},\{pagamento.isPagamentoProcessado() ? "1" : "0"}");
                writer.newLine();
            }
        }
    }

    public List<Pagamento> carregarPagamentos(Map<Integer, Pedido> pedidos) throws IOException, FormatoArquivoException {
        List<Pagamento> pagamentos = new ArrayList<>();
        File file = new File(ARQUIVO_FINANCEIRO);
        if (!file.exists()) {
            throw new ArquivoNaoEncontradoException("Arquivo financeiro não encontrado.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 4) {
                    throw new FormatoArquivoException("Formato inválido no arquivo financeiro.");
                }

                try {
                    int idPagamento = Integer.parseInt(partes[0]);
                    int idPedido = Integer.parseInt(partes[1]);
                    int valor = Integer.parseInt(partes[2]);
                    boolean pagamentoProcessado = partes[3].equals("1");

                    Pedido pedido = pedidos.get(idPedido);
                    if (pedido == null) {
                        throw new FormatoArquivoException("Pedido não encontrado para o pagamento.");
                    }

                    Pagamento pagamento = new Pagamento(idPagamento, pedido, valor);
                    if (pagamentoProcessado) {
                        pagamento.processarPagamento(0, 0, valor);
                    }

                    pagamentos.add(pagamento);
                } catch (NumberFormatException e) {
                    throw new FormatoArquivoException("Erro ao formatar número no arquivo financeiro.");
                } catch (DadosInvalidosException e) {
                    throw new FormatoArquivoException(STR."Dados inválidos ao processar pagamento: \{e.getMessage()}");
                } catch (PagamentoInvalidoException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return pagamentos;
    }
}
