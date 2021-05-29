package br.com.codificando.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.codificando.model.Post;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.PostRepository;
import br.com.codificando.repository.UsuarioRepository;

@Service
public class PostService{
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;

	public Post save(Post post, MultipartFile multipartFile) {
			Usuario usuario = usuarioService.usuarioLogado();
			LocalDateTime dataAtual = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataFormatada = dataAtual.format(formatter);
			post.setData(dataFormatada);
			post.setUsuario(usuario);
			try {
				if(multipartFile.getOriginalFilename() != "" || multipartFile.getOriginalFilename() != null) {
					post.setCheckImagem(true);
					post.setImagem(multipartFile.getBytes());
				}else {
					post.setCheckImagem(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			usuario.setPost(post);
	        postRepository.save(post);
			return post;
	}
	
	public void getImagem(long id, HttpServletResponse response)throws IOException {
		Post post = postRepository.findById(id);
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(post.getImagem());
		IOUtils.copy(is, response.getOutputStream());
	}
	
	public List<Post> list() {
		Usuario usuario = usuarioService.usuarioLogado();
		return postRepository.findPostsAmigos(usuario.getId());
	}
	
	public List<Post> listPostsUsuario(String login){
		Usuario usuario = usuarioRepository.findByLogin(login);
		return postRepository.findPostsUsuario(usuario.getId());
	}
}
	
	

