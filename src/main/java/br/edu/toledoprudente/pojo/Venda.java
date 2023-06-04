package br.edu.toledoprudente.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@EntityScan
@Table(name = "vendas")
public class Venda extends AbstractEntity<Integer> {

	@NotNull(message = "Informe uma data!")
	@Column(name = "data", nullable = false, columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate data;

	@NotNull(message = "Informe o valor da venda!")
	@PositiveOrZero(message = "O valor deve ser positivo")
	@Column(name = "valor_total", columnDefinition = "DECIMAL(10,2) DEFAULT 0.00", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private Double valorTotal;

	@NotNull(message = "Informe um cliente")
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVenda> itens = new ArrayList<>();

	public LocalDate getData() {
		return data;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
