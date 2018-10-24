package com.sistema.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

//classe de serviço para amazon S3
@Service
public class S3Service {

	// logger para erro do servidor
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	// injeção de dependência da classe de configuração do pacote config
	@Autowired
	private AmazonS3 s3client;

	// string atribuida para nome do bucket
	@Value("${s3.bucket}")
	private String bucketName;

	// método responsável por fazer o upload do arquivo com uma string de local file
	// onde será direcionado para o bucket
	public URI uploadFile(MultipartFile multiPartFile) {
		try {
			String fileName = multiPartFile.getOriginalFilename();
			InputStream inputStream = multiPartFile.getInputStream();
			String contentType = multiPartFile.getContentType();
			return uploadFile(inputStream, fileName, contentType);
			} catch (IOException e) {
				throw new RuntimeException("Erro IO:" + e.getMessage());
			}
			
			
	}

	
	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
	
		try {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		LOG.info("Iniciando Upload");
		s3client.putObject(bucketName, fileName, inputStream, metadata);
		LOG.info("Upload Finalizado");
		
			
		return s3client.getUrl(bucketName, fileName).toURI();
		
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao tentar converter URL para URI");
		}

	}
	
}	
