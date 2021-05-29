package br.com.codificando.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.codificando.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
	
	public List<Report> findByAssunto(String assunto);
	
	public Report findById(long id);

}
