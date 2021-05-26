package br.com.codificando.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codificando.model.Permissao;
import br.com.codificando.model.Usuario;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

	public List<Permissao> findByUsuariosIn(Usuario ... usuario);
}
