package br.com.codificando.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario usuarioLogado() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepository.findByLogin(login);
	}
	
	public String salvar(Usuario usuario) throws IOException{
		 try {
			 if(usuario != null) {
		          String senha = usuario.getSenha();
		          usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		          usuarioRepository.save(usuario);
		          usuario = usuarioRepository.findByLogin(usuario.getLogin());
		          usuarioRepository.insetRole(usuario.getId());
		     }
			 
	      }catch (Exception e) {
	            System.out.println("Erro ao salvar: " + e.getMessage());
	            return "redirect:/login-error";
	      }
	      return "redirect:/login";
	}
}
