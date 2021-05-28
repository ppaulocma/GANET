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
import br.com.codificando.model.Report;
import br.com.codificando.repository.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
	ReportRepository reportReposytory;
	
	 public void reportar(Report report, MultipartFile multipartFile)throws IOException {
		 try {
			 LocalDateTime dataAtual = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			 String dataFormatada = dataAtual.format(formatter);
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         report.setData(dataFormatada);
	         report.setImg(fileName);
	         reportReposytory.save(report);
	         if(fileName != null || fileName != "") {
	        	 String uploadDir = "usuario-fotos/report/" + report.getId();          
	        	 UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	         }
	         reportReposytory.save(report);
	     } catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	     }
	 }
	 
	 public List<Report> listarPorAssunto(String assunto) {	
		 if(assunto == null || assunto == "") {
			 return reportReposytory.findAll();
		 }else {
			 return reportReposytory.findByAssunto(assunto);
		 }
	}
}
