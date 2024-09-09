package Arquivos;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;
import Exceptions.ArquivoNaoEncontradoException;
import Exceptions.FormatoArquivoException;
import java.io.*;
import java.util.*;

public class ArquivoEstoque {

    private static final String ARQUIVO_ESTOQUE = "estoque.txt";

    public void salvarEstoque(List<Produto> produtos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ESTOQUE))) {
            for (Produto produto : produtos) {
                writer.write(STR."\{produto.getId()},\{produto.getNome()},\{produto.getDescricao()},\{produto.getPreco()},\{produto.getCategoria()},\{produto.getCodigoBarra()},\{produto.getQuantidade()}");
                writer.newLine();
            }
        }
    }

    public List<Produto> carregarEstoque() throws IOException, FormatoArquivoException {
        List<Produto> produtos = new ArrayList<>();
        File file = new File(ARQUIVO_ESTOQUE);
        if (!file.exists()) {
            throw new ArquivoNaoEncontradoException("Arquivo de estoque não encontrado.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 7) {
                    throw new FormatoArquivoException("Formato inválido no arquivo de estoque.");
                }

                try {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String descricao = partes[2];
                    double preco = Double.parseDouble(partes[3]);
                    String categoria = partes[4];
                    String codigoBarra = partes[5];
                    int quantidade = Integer.parseInt(partes[6]);

                    Produto produto = new Produto(id, nome, descricao, preco, categoria, codigoBarra, quantidade);
                    produtos.add(produto);
                } catch (NumberFormatException | DadosInvalidosException e) {
                    throw new FormatoArquivoException("Erro ao formatar número no arquivo de estoque.");
                }
            }
        }
        return produtos;
    }
}
