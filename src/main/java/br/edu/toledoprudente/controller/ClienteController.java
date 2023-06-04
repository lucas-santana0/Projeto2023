package br.edu.toledoprudente.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.toledoprudente.dao.ClienteDAO;
import br.edu.toledoprudente.pojo.Cliente;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteDAO dao;

	@GetMapping("/index")
	public String index(ModelMap model) {
		model.addAttribute("cliente", new Cliente());
		return "/cliente/index";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lista", dao.findAll());
		return "/cliente/listar";
	}

	@GetMapping("/preAlterar")
	public String preAlterar(@RequestParam(name = "id") int id, ModelMap model) {
		model.addAttribute("cliente", dao.findById(id));
		return "/cliente/index";
	}

	@GetMapping("/excluir")
	public String excluir(@RequestParam(name = "id") int id, ModelMap model) {
		try {
			dao.delete(id);
			model.addAttribute("mensagem", "Exclusão efetuada");
			model.addAttribute("retorno", true);
		} catch (Exception e) {
			model.addAttribute("mensagem", "Exclusão não pode ser efetuada!");
			model.addAttribute("retorno", false);
		}

		model.addAttribute("lista", dao.findAll());
		return "/cliente/listar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("cliente") Cliente cli, ModelMap model) {
		try {
			Validator validator;
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
			Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cli);
			String errors = "";

			for (ConstraintViolation<Cliente> constraintViolation : constraintViolations) {
				errors = errors + constraintViolation.getMessage() + ". ";
			}

			if (errors != "") {
				model.addAttribute("cliente", cli);
				model.addAttribute("mensagem", errors);
				model.addAttribute("retorno", false);
				return "/cliente/index";
			} else {
				if (cli.getId() == null) {
					dao.save(cli);
				} else {
					dao.update(cli);
				}

				model.addAttribute("mensagem", "Salvo com sucesso!");
				model.addAttribute("retorno", true);
			}
		} catch (Exception e) {
			model.addAttribute("mensagem", "Erro ao salvar!" + e.getMessage());
			model.addAttribute("retorno", false);
		}

		return "/cliente/index";
	}

	@GetMapping(path = "/listaCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProdutos() {
		List<Cliente> clientes = dao.findAll();
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}
}
