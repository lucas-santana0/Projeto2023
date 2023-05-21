package br.edu.toledoprudente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.toledoprudente.dao.ClienteDAO;
import br.edu.toledoprudente.dao.ProdutoDAO;
import br.edu.toledoprudente.pojo.Cliente;
import br.edu.toledoprudente.pojo.Produto;

@Controller
@RequestMapping("/venda")
public class VendaController {

	@Autowired
	ProdutoDAO daoproduto;

	@Autowired
	ClienteDAO daocliente;

	@GetMapping("/index")
	public String index() {
		return "/venda/index";
	}

	@ModelAttribute(name = "listaproduto")
	public List<Produto> listaProduto() {
		return daoproduto.findAll();
	}

	@ModelAttribute(name = "listacliente")
	public List<Cliente> listaCliente() {
		return daocliente.findAll();
	}

	@PostMapping(path = "/salvarProduto", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Produto pro) {
		try {

			if (pro.getId() == null)
				daoproduto.save(pro);
			else
				daoproduto.update(pro);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return new ResponseEntity<Object>(pro, HttpStatus.OK);
	}
}
