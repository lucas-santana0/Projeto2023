package br.edu.toledoprudente.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.toledoprudente.dao.FuncionarioDAO;
import br.edu.toledoprudente.pojo.AppAuthority;
import br.edu.toledoprudente.pojo.Funcionario;
import br.edu.toledoprudente.pojo.Users;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioDAO dao;

	@GetMapping("/index")
	public String index(ModelMap model) {
		Funcionario fun = new Funcionario();
		fun.setUsuario(new Users());
		model.addAttribute("funcionario", fun);
		return "/funcionario/index";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lista", dao.findAll());
		return "/funcionario/listar";
	}

	@GetMapping("/preAlterar")
	public String preAlterar(@RequestParam(name = "id") int id, ModelMap model) {
		model.addAttribute("funcionario", dao.findById(id));
		return "/funcionario/index";
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
		return "/funcionario/listar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("funcionario") Funcionario fun, ModelMap model) {
		try {
			Validator validator;
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
			Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validate(fun);
			String errors = "";
			
			for (ConstraintViolation<Funcionario> constraintViolation : constraintViolations) {
				errors = errors + constraintViolation.getMessage() + ". ";
			}
			
			if (errors != "") {
				model.addAttribute("funcionario", fun);
				model.addAttribute("mensagem", errors);
				model.addAttribute("retorno", false);
				return "/funcionario/index";		
			} else {
				Users usu = fun.getUsuario();
				String senha = "{bcrypt}" + new BCryptPasswordEncoder().encode(usu.getPassword());
				usu.setPassword(senha);
				usu.setEnabled(true);
				usu.setAdmin(false);

				Set<AppAuthority> appAuthorities = new HashSet<AppAuthority>();
				AppAuthority app = new AppAuthority();
				app.setAuthority("USER");
				app.setUsername(usu.getUsername());
				appAuthorities.add(app);
				usu.setAppAuthorities(appAuthorities);
				
				if (fun.getId() == null) {
					dao.save(fun);
				} else {
					dao.update(fun);
				}

				model.addAttribute("mensagem", "Salvo com sucesso!");
				model.addAttribute("retorno", true);
			}
		} catch (Exception e) {
			model.addAttribute("mensagem", "Erro ao salvar!" + e.getMessage());
			model.addAttribute("retorno", false);
		}

		return "/funcionario/index";
	}
}
