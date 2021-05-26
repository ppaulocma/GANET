package br.com.codificando.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.codificando.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
	@Query(value="select m.* from mensagem m where m.message = :mensagem" , nativeQuery = true)
	public Mensagem findByMensagem(@Param("mensagem") String mensagem);
	
	@Query(value="select m.* from mensagem m where from_login = :login or para = :login" , nativeQuery = true)
	public List<Mensagem> findHistorico(@Param("login") String login);

	@Query(value="select m.* from mensagem m where from_login = :login and para = :login2 or from_Login = :login2 and para = :login" , nativeQuery = true)
	public List<Mensagem> findMensagens(@Param("login") String login, @Param("login2") String login2);
	
	@Query(value="select m.* from mensagem m where from_login = :login and para = :login2 and status = :status" , nativeQuery = true)
	public List<Mensagem> getMsgByStatus(@Param("login") String login, @Param("login2") String login2, long status);
	
	@Query(value="select m.status from mensagem m where from_login = :login and para = :login2 and status = :status" , nativeQuery = true)
	public List<Long> getStatus(@Param("login") String login, @Param("login2") String login2, long status);
	
	@Query(value="select m.id from mensagem m where from_login = :login and para = :login2" , nativeQuery = true)
	public List<Long> findId(@Param("login") String login, @Param("login2") String login2);
}
