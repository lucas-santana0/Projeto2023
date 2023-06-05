package br.edu.toledoprudente.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "itens_venda")
public class ItemVenda extends AbstractEntity<Integer> {

    @NotNull(message = "Informe um produto")
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @NotNull(message = "Informe a quantidade")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
