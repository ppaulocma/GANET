package br.com.codificando.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codificando.model.Comentario;
import br.com.codificando.model.Post;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.ComentarioRepository;
import br.com.codificando.repository.PostRepository;

@Service
public class ComentarioService {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ComentarioRepository comentarioRepository;
	
	public Comentario comentar(long id, Comentario comentario){
		Usuario usuario = usuarioService.usuarioLogado();
		Post post = postRepository.findById(id);
		comentario.setPost(post);
		comentario.setUsuario(usuario);
		post.setComentario(comentario);
		comentarioRepository.save(comentario);
		return comentario;
	}
}
