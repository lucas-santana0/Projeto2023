package br.edu.toledoprudente.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.toledoprudente.dao.CategoriaDAO;
import br.edu.toledoprudente.pojo.Categoria;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaDAO dao;

	@GetMapping("/index")
	public String index(ModelMap model) {
		model.addAttribute("categoria", new Categoria());
		return "/categoria/index";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lista", dao.findAll());
		return "/categoria/listar";
	}

	@GetMapping("/preAlterar")
	public String preAlterar(@RequestParam(name = "id") int id, ModelMap model) {
		model.addAttribute("categoria", dao.findById(id));

		return "/categoria/index";
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
		return "/categoria/listar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("categoria") Categoria cat, ModelMap model) {
		try {

			Validator validator;
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
			Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cat);
			String errors = "";
			
			for (ConstraintViolation<Categoria> constraintViolation : constraintViolations) {
				errors = errors + constraintViolation.getMessage() + ". ";
			}

			if (errors != "") {
				model.addAttribute("categoria", cat);
				model.addAttribute("mensagem", errors);
				model.addAttribute("retorno", false);
				return "/categoria/index";
			} else {

				if (cat.getId() == null) {
					dao.save(cat);
				} else {
					dao.update(cat);
				}

				model.addAttribute("mensagem", "Salvo com sucesso!");
				model.addAttribute("retorno", true);
			}
		} catch (Exception e) {
			model.addAttribute("mensagem", "Erro ao salvar!" + e.getMessage());
			model.addAttribute("retorno", false);
		}

		return "/categoria/index";
	}

}
