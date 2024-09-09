package Arquivos;

import Negocio.Entrega;
import java.io.*;
import Exceptions.*;

public class ArquivoEntregas {
    private static final String NOME_ARQUIVO_ENTREGAS = "entregas.csv";

    public static void salvarEntrega(Entrega entrega) throws IOException {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO_ENTREGAS, true)) {
            writer.write(entrega.getIdEntrega() + "," + entrega.getTipoEntrega() + "," + entrega.getTaxaEntrega() + "\n");
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Erro ao salvar a entrega: arquivo não encontrado.");
        }
    }

    public static void carregarEntregas() throws IOException, FormatoArquivoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO_ENTREGAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    throw new FormatoArquivoException("Erro no formato do arquivo de entregas.");
                }
             
                int idEntrega = Integer.parseInt(partes[0]);
                String tipoEntrega = partes[1];
                int taxa = Integer.parseInt(partes[2]);
                System.out.println("ID: " + idEntrega + " | Tipo: " + tipoEntrega + " | Taxa: " + taxa);
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de entregas não encontrado.");
        }
    }
}
