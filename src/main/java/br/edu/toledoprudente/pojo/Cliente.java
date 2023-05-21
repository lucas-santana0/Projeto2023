package br.edu.toledoprudente.pojo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente extends AbstractEntity<Integer> {

	@NotBlank(message = "Informe um nome!")
	@Size(min = 3, max = 150, message = "O nome deve ter entre 3 a 150 caracteres!")
	@Column(length = 150, nullable = false)
	private String nome;

	@NotBlank(message = "Informe um email!")
	@Column(length = 150, nullable = false)
	private String email;

	@NotNull(message = "Informe uma data de nascimento!")
	@Column(name = "dataNascimento", nullable = false, columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate datanascimento;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idUsuario")
	private Users usuario;
	
	@OneToMany(mappedBy = "cliente")
	private List<Venda> vendas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(LocalDate datanascimento) {
		this.datanascimento = datanascimento;
	}

	public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
