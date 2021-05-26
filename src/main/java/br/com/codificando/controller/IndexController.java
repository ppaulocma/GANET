package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;

@Controller
public class IndexController {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/")
		public String getIndex(){
			return "index";
	}
	
	@GetMapping("/chat")
	public String getChat(Model model){
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario= usuarioRepository.findByLogin(login);
		model.addAttribute("usuario", usuario);
		return "chat";
	}
}
