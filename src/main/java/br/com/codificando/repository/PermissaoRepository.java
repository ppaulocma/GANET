package br.com.codificando.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.codificando.model.Permissao;
import br.com.codificando.model.Usuario;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

	public List<Permissao> findByUsuariosIn(Usuario ... usuario);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="insert into permissao (nome) value ('ADMIN')" , nativeQuery = true)
	public void insetPermissao1();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="insert into permissao (nome) value ('USER')" , nativeQuery = true)
	public void insetPermissao2();
}
