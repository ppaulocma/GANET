package br.com.codificando.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;

@Service
public class PerfilService {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public boolean checkPerfil(long idPerfil) {
		Long idLogado = usuarioService.usuarioLogado().getId();
		if(idPerfil == idLogado) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkSeguidor(long idPerfil) {
		Long idLogado = usuarioService.usuarioLogado().getId();
		String check = usuarioRepository.checkSeguidor(idPerfil ,idLogado);
		return check.equals("1");
	}
	
	public String seguir (long id) {
		Usuario usuario = usuarioService.usuarioLogado();
		Usuario amigo = usuarioRepository.findById(id);
		usuario.setAmigo(amigo);
		usuarioRepository.save(usuario);
		return "redirect:/perfil/"+amigo.getLogin();
	}
	
	public void deixarDeSeguir (long id) {
		Usuario usuario = usuarioService.usuarioLogado();
		usuarioRepository.deleteSeguindo(id, usuario.getId());
	}
	
	public void removeSeguidor (long id) {
		Usuario usuario = usuarioService.usuarioLogado();
		usuarioRepository.deleteSeguidor(id, usuario.getId());
	}
	
}
