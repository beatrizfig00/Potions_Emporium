package Negocio;

import Exceptions.DadosInvalidosException;
import Exceptions.ItemNaoEncontradoException;
import Exceptions.PromoInvalidaException;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private final List<ItemCarrinho> itens;
    private double total;

    public Carrinho() {
        this.itens = new ArrayList<>();
        this.total = 0.0;
    }

    public void adicionarItem(Produto produto, int quantidade, double preco) throws DadosInvalidosException {
        if (produto == null || quantidade <= 0 || preco <= 0) {
            throw new DadosInvalidosException("Dados do item inválidos.");
        }
        this.itens.add(new ItemCarrinho(produto, quantidade, preco));
        this.total += quantidade * preco;
    }

    public void removerItem(Produto produto) throws ItemNaoEncontradoException {
        ItemCarrinho item = this.itens.stream()
                .filter(i -> i.produto().equals(produto))
                .findFirst()
                .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado no carrinho."));

        this.itens.remove(item);
        this.total -= item.quantidade() * item.preco();
    }

    public double calculaTotal() {
        return this.total;
    }

    public void aplicarDesconto(double percentual) throws PromoInvalidaException {
        if (percentual < 0 || percentual > 100) {
            throw new PromoInvalidaException("Percentual de desconto inválido.");
        }
        this.total -= this.total * (percentual / 100);
    }

        private record ItemCarrinho(Produto produto, int quantidade, double preco) {
    }
}
