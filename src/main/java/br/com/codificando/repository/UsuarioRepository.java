package br.com.codificando.repository;


import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.codificando.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findById(long id);

	public Usuario findByLogin(String login);
	
	@Query(value="select count(*) > 0 from usuario_amigos where amigos_id = :id1 && usuario_id = :id2" , nativeQuery = true)
	public String checkSeguidor(@Param("id1") long id1,@Param("id2") long id2 );
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from usuario_amigos where amigos_id = :id1 AND usuario_id = :id2" , nativeQuery = true)
	void deleteSeguindo(@Param("id1") long id1,@Param("id2") long id2 );
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from usuario_amigos where amigos_id = :id2 AND usuario_id = :id1" , nativeQuery = true)
	void deleteSeguidor(@Param("id1") long id1,@Param("id2") long id2 );
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="insert into usuario_permissao (usuario_id , permissao_id) value (:id, 2)" , nativeQuery = true)
	public void insetRole(@Param("id") long id);
	
	@Query(value="select u.* from usuario u inner join usuario_amigos ua on ua.usuario_id = u.id where ua.amigos_id = :id" , nativeQuery = true)
	public List<Usuario> getSeguidores(@Param("id") long id);
	
}
