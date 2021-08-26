package com.example.projeto.controllers;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projeto.dto.RequisicaoNovoUsuario;
import com.example.projeto.model.Users;
import com.example.projeto.repository.UserRepository;

@Controller
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	// get method
	@GetMapping(value = "/mainLayout")
	public String mainLayout(Model model) {
		return "layouts/mainLayout";
	}

	// get method
	@GetMapping(value = "/cadastro")
	public String cadastro(RequisicaoNovoUsuario req, HttpServletRequest http) throws ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& !("anonymousUser").equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			http.logout();
			return "redirect:/cadastro";
		}
		return "paginas/cadastro";
	}

	// BD method CREATE
	@PostMapping(value = "/users")
	public String cadastro(@Valid RequisicaoNovoUsuario req, BindingResult result, Model model, RedirectAttributes redirAttrs) {
		
		if (result.hasErrors()) {
			for (Object object : result.getAllErrors()) {
				if (object instanceof FieldError) {
					System.out.println(messageSource.getMessage((FieldError) object, null));
				}
			}
			model.addAttribute("msg", "Cadastro Inválido");
			return "paginas/cadastro";

		} else if (!req.isUpdate()) {
			for (Users u : userRepository.findAll()) {
				if (u.getEmail().equals(req.getEmail())) {
					model.addAttribute("msg", "Email já cadastrado. Tente outro.");
					return "paginas/cadastro";
					//throw new IllegalArgumentException("Email já cadastrado. Tente outro.");
				}
			}
			
		} if(!req.getSenha().equals(req.getSenha_confirmacao())) {
			model.addAttribute("msg", "Senha e confirmação de senha estão diferentes!");
			return "paginas/cadastro";
		}
		
		Users usuario = req.toUsers();
		userRepository.save(usuario);
		jdbcUserDetailsManager.updateUser(usuario);
		redirAttrs.addFlashAttribute("msg", "Operação realizada com sucesso!");
		return "redirect:/";
	}

	/* BD method READ
	@GetMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("usuarios", userRepository.findAll());
		return "paginas/list";
	} */

	// BD method UPDATE
	@PostMapping("cadastroUp/{id}")
	public String updateUser(@PathVariable(value = "id") Long id, RequisicaoNovoUsuario req, Model model) {
		Optional<Users> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			model.addAttribute("msg", "Usuário Inválido");
			return "paginas/cadastroUp";
			//throw new IllegalArgumentException("Usuário Inválido");
		}
		model.addAttribute("usuario", userOpt.get().toReq());
		return "paginas/cadastroUp";
	}

	// BD method DELETE
	@PostMapping("deletar/{id}")
	public String deleteUser(@PathVariable(value = "id") Long id, RequisicaoNovoUsuario req, Model model,
			HttpServletRequest http, RedirectAttributes redirAttrs) throws ServletException {
		Optional<Users> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			redirAttrs.addFlashAttribute("msg", "Usuário Inválido");
			return "redirect:/";
			//throw new IllegalArgumentException("Usuário Inválido");
		}
		jdbcUserDetailsManager.deleteUser(userOpt.get().getEmail());
		redirAttrs.addFlashAttribute("msg", "Usuário deletado com sucesso!");
		http.logout();
		return "redirect:/";
	}

	// get method
	@GetMapping(value = "/comissoes")
	public String comissoes(Model model) {
		return "paginas/comissoes";
	}

	// get method
	@GetMapping(value = "/contato")
	public String contato(Model model) {
		return "paginas/contato";
	}

	// get method
	@GetMapping(value = "/index")
	public String index(Model model) {
		return "index";
	}

	// get method
	@GetMapping(value = "/login")
	public String login(Model model,  HttpServletRequest http) throws ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& !("anonymousUser").equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			http.logout();
			return "redirect:/login";
		}
		return "login";
	}

	// get method
	@GetMapping(value = "/normasanais")
	public String normasanais(Model model) {
		return "paginas/normasanais";
	}

	// get method
	@GetMapping(value = "/normas-de-inscricoes")
	public String normasDeInscricoes(Model model) {
		return "paginas/normas-de-inscricoes";
	}

	// get method
	@GetMapping(value = "/normasebook")
	public String normasebook(Model model) {
		return "paginas/normasebook";
	}

	// get method
	@GetMapping(value = "/portal")
	public String portal(Model model) {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Users> userOpt = userRepository.findByEmail(u.getUsername());
		if (userOpt.get().isPresenca()) {
			model.addAttribute("msg", "Desmarcar Presença no Evento");
			model.addAttribute("msg2", "Prensença confirmada para o próximo dia do evento, te aguardamos lá. Boa sorte.");
		} else {
			model.addAttribute("msg", "Marcar Presença no Evento");
		}
		model.addAttribute("usuario", userOpt.get());
		return "paginas/portal";
	}
	
	@PostMapping(value = "/presenca")
	public String presenca(Model model) {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Users> userOpt = userRepository.findByEmail(u.getUsername());
		
		if (userOpt.get().isPresenca()) {
			userOpt.get().setPresenca(false);
		} else {
			userOpt.get().setPresenca(true);
		}
		
		userRepository.save(userOpt.get());		
		return "redirect:/portal";
	}

	// get method
	@GetMapping(value = "/programacao")
	public String programacao(Model model) {
		return "paginas/programacao";
	}

	// get method
	@GetMapping(value = "/sobre")
	public String sobre(Model model) {
		return "paginas/sobre";
	}
}
