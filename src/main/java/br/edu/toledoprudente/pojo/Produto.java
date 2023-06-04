package br.edu.toledoprudente.pojo;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "produtos")
public class Produto extends AbstractEntity<Integer> {

	@NotBlank(message = "Informe o nome")
	@Size(min = 3, max = 100, message = "O nome deve conter de 3 a 150 caracteres")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@NotBlank(message = "Informe a descrição")
	@Size(min = 3, message = "A descrição deve conter no mínimo 3 caracteres")
	@Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
	private String descricao;

	@NotNull(message = "Informe o preço")
	@PositiveOrZero(message = "O preço deve ser positivo")
	@Column(name = "preco", columnDefinition = "DECIMAL(10,2) DEFAULT 0.00", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal preco;

	@Column(name = "estoque", columnDefinition = "INT", nullable = false)
	private Integer estoque;

	@Column(name = "imagem", length = 300)
	private String imagem;

	@JsonBackReference
	@NotNull(message = "Informe uma categoria")
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
}
