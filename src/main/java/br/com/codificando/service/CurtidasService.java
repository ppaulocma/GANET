package br.com.codificando.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codificando.model.Curtida;
import br.com.codificando.model.Post;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.CurtidaRepository;
import br.com.codificando.repository.PostRepository;

@Service
public class CurtidasService {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CurtidaRepository curtidaRepository;
	
	public Curtida curtir(long id){
		Usuario usuario = usuarioService.usuarioLogado();
		Post post = postRepository.findById(id);
		if(checkCurtida(id)) {
			removerCurtida(id, usuario.getId());
			return null;
		}else {
		Curtida curtida = new Curtida();
		curtida.setPost(post);
		curtida.setUsuario(usuario);
		post.setCurtida(curtida);
		curtidaRepository.save(curtida);
		return curtida;
		}
	}
	
	public void removerCurtida(long postId, long userId) {
		curtidaRepository.removerCurtida(postId, userId);
	}
	
	public boolean checkCurtida(long id) {
		Usuario usuario = usuarioService.usuarioLogado();
		Post post = postRepository.findById(id);
		List<Curtida> curtidas = post.getCurtidas();
		for (int i = 0; i < curtidas.size(); i++) {
    		Curtida curtida = curtidas.get(i);
    		if(curtida.getUsuario().equals(usuario)) {
    			return true;
    		}
		}
		return false;
	}
}
