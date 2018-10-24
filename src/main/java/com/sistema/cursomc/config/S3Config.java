package com.sistema.cursomc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

//classe de configutação do bucket amazon S3
@Configuration
public class S3Config {

	//chave da acesso
	@Value("${aws.access_key_id}")
	private String awsId;
	
	//chave secret de acesso
	@Value("${aws.secret_access_key}")
	private String awsKey;
	
	
	//região escolhida na criação do bucket
	@Value("${s3.region}")
	private String region;
	
	//método de validação para acesso ao bucket
	@Bean
	public AmazonS3 s3client() {
		
		BasicAWSCredentials awsCred = new BasicAWSCredentials(awsId, awsKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
							.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
		return s3client;
	}
	
	
	
}
