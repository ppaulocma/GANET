package br.com.codificando.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import br.com.codificando.UploadFotos;
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

	public Post save(Post post, MultipartFile multipartFile)throws IOException {
		try {
			Usuario usuario = usuarioService.usuarioLogado();
			LocalDateTime dataAtual = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataFormatada = dataAtual.format(formatter);
			post.setData(dataFormatada);
			post.setUsuario(usuario);
			usuario.setPost(post);
			postRepository.save(post);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        String uploadDir = "usuario-fotos/post/" + post.getId();
	        if (fileName == null || fileName == "") {
				post.setImg(null);
			}else{
				post.setImg(fileName);
				UploadFotos.saveFile(uploadDir, fileName, multipartFile);
			}
	        postRepository.save(post);
			return post;
			
		} catch (Exception e) {
			System.out.println("Erro ao Salvar: " + e.getMessage());
			return null;
		}
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
	
	

