package br.edu.toledoprudente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.toledoprudente.dao.UsersDAO;
import br.edu.toledoprudente.pojo.Cliente;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	UsersDAO dao;

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@GetMapping("/cadastro")
	public String cadatro(ModelMap model) {
		model.addAttribute("cliente", new Cliente());
		return "/cadastro";
	}

	@GetMapping("/")
	public String index(ModelMap model) {
		model.addAttribute("nomeusuario", dao.getUsuarioLogado().getNome());
		return "index";
	}

	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("mensagem", "Dados inválidos");
		return "/login";
	}

}
