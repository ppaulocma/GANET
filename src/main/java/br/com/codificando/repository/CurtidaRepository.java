package br.com.codificando.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.codificando.model.Curtida;
import br.com.codificando.model.Post;

public interface CurtidaRepository extends JpaRepository<Curtida, Long>{
	
	public  List<Curtida> findByPost (Post post);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from curtida where post_id= :id1 AND usuario_id= :id2" , nativeQuery = true)
	void removerCurtida(@Param("id1") long id1,@Param("id2") long id2 );

}
