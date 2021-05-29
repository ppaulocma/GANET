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
import br.com.codificando.model.Report;
import br.com.codificando.repository.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
	ReportRepository reportReposytory;
	
	 public void reportar(Report report, MultipartFile multipartFile)throws IOException {
			 LocalDateTime dataAtual = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			 String dataFormatada = dataAtual.format(formatter);
	         report.setData(dataFormatada);
	         try {
					if(multipartFile.getOriginalFilename() != "") {
						report.setCheckImagem(true);
						report.setImagem(multipartFile.getBytes());
					}else {
						report.setCheckImagem(false);
						report.setImagem(null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	         reportReposytory.save(report);
	 }
	 
	 public void getImagem(long id, HttpServletResponse response)throws IOException {
			Report report = reportReposytory.findById(id);
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(report.getImagem());
			IOUtils.copy(is, response.getOutputStream());
		}
	 
	 public List<Report> listarPorAssunto(String assunto) {	
		 if(assunto == null || assunto == "") {
			 return reportReposytory.findAll();
		 }else {
			 return reportReposytory.findByAssunto(assunto);
		 }
	}
}
