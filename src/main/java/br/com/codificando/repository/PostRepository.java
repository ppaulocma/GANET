package br.com.codificando.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.codificando.model.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	public Post findById(long id);	
	
	@Query(value = "select p.* from post p where p.usuario_id in (select ua.amigos_id as id from usuario_amigos ua where ua.usuario_id = :id union select u.id from usuario u where u.id = :id) order by p.data desc", nativeQuery = true)
	public List<Post> findPostsAmigos(@Param("id") long id);
	
	@Query(value = "select p.* from post p where p.usuario_id = :id order by p.data desc", nativeQuery = true)
	public List<Post> findPostsUsuario(@Param("id") long id);
	
}
