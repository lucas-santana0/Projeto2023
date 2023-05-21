package br.edu.toledoprudente.pojo;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@EntityScan
@Table(name="categorias")
public class Categoria 
extends AbstractEntity<Integer> {
		
		@NotBlank(message = "Informe a descrição")
		@Size(min = 3, max = 150, message = "A descrição deve conter de 3 a 150 caracteres")
		@Column(length = 150, nullable = false)
		private String descricao;

		@JsonManagedReference
		@OneToMany(mappedBy = "categoria")
		private List<Produto> produtos;
		
		public List<Produto> getProdutos() {
			return produtos;
		}

		public void setProdutos(List<Produto> produtos) {
			this.produtos = produtos;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
	
}
