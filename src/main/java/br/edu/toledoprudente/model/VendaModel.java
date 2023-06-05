package br.edu.toledoprudente.model;

import java.util.List;

public class VendaModel {
	public ClienteModel cliente;
    public List<ItemVendaModel> itens;
    
	public ClienteModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}
	public List<ItemVendaModel> getItens() {
		return itens;
	}
	public void setItens(List<ItemVendaModel> itens) {
		this.itens = itens;
	}
}
