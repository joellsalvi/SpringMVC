package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileRecorder {
	
	@Autowired
	private HttpServletRequest request;

	public String resolveFile(String basePath, MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + basePath);
			String filePath = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(filePath));
			return basePath + "/" + file.getOriginalFilename();
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
