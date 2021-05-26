package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.codificando.repository.UsuarioRepository;

@Controller
public class IndexController {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/")
		public String getIndex(){
			return "index";
	}

}
