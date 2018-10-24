package com.sistema.cursomc.services;

import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
	public void uploadFile(String localFilePath) {

		try {
			File file = new File(localFilePath);

			LOG.info("Iniciando Upload");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			LOG.info("Upload Finalizado");

		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException:" + e.getErrorMessage());
			LOG.info("Status Code:" + e.getErrorCode());

		} catch (AmazonClientException e) {
			LOG.info("AmazonClientException:" + e.getMessage());
		}
	}

}
