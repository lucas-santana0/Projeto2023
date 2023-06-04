package br.edu.toledoprudente.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.toledoprudente.dao.ClienteDAO;
import br.edu.toledoprudente.dao.ItemVendaDAO;
import br.edu.toledoprudente.dao.ProdutoDAO;
import br.edu.toledoprudente.dao.VendaDAO;
import br.edu.toledoprudente.model.ItemVendaModel;
import br.edu.toledoprudente.model.VendaModel;
import br.edu.toledoprudente.pojo.Cliente;
import br.edu.toledoprudente.pojo.ItemVenda;
import br.edu.toledoprudente.pojo.Produto;
import br.edu.toledoprudente.pojo.Venda;

@Controller
@RequestMapping("/venda")
public class VendaController {

	@Autowired
	private ClienteDAO daoCliente;

	@Autowired
	private VendaDAO daoVenda;

	@Autowired
	private ProdutoDAO daoProduto;

	@Autowired
	private ItemVendaDAO daoItemVenda;

	@GetMapping("/index")
	public String index(ModelMap model) {
		model.addAttribute("venda", new Venda());
		return "/venda/index";
	}

	@PostMapping(path = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody VendaModel vendaModel) {
		try {
			Venda venda = new Venda();
			Cliente cliente = daoCliente.findById(vendaModel.getCliente().getId());
			venda.setCliente(cliente);
			venda.setData(LocalDate.now());
			
			List<ItemVendaModel> itensVenda = vendaModel.getItens();
			
			double valorTotal = 0;

			for (ItemVendaModel itemVenda : itensVenda) {
				valorTotal += itemVenda.getTotal();
			}

			venda.setValorTotal(valorTotal);

			daoVenda.save(venda);

			for (ItemVendaModel itemVenda : itensVenda) {
				ItemVenda item = new ItemVenda();
				Produto produto = daoProduto.findById(itemVenda.getIdProduto());
				item.setProduto(produto);
				item.setQuantidade(itemVenda.getQuantidade());
				item.setVenda(venda);
				daoItemVenda.save(item);
				produto.setEstoque(produto.getEstoque() - itemVenda.getQuantidade());
				daoProduto.save(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(vendaModel, HttpStatus.OK);
	}
}
