package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.demo.entity.FileUploadEntity;
import com.example.demo.repo.FileUploadRepository;
import com.example.demo.service.FileService;
import com.example.demo.utility.FileUtility;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private AmazonS3Client amazonClient;
	
	@Autowired
	private FileUploadRepository fileUploadRepository;
	

	@Value("${app.awsServices.bucketName}")
	private String bucketName;
	
	@Value("${app.fileUploadLocation}")
	private String fileUploadLocation;

	@Override
	public String uploadFile(MultipartFile file) {

		 String originalFileName = file.getOriginalFilename();
		    try {
		    	
			Path fileStorageLocation = Paths.get(fileUploadLocation).toAbsolutePath().normalize();
			String fileName = FileUtility.getFileNameCheck(fileStorageLocation, file);
			Path targetLocation = fileStorageLocation.resolve(fileName);
						
			Files.copy(file.getInputStream(), targetLocation);
			
			Timestamp rightNow = new Timestamp(new java.util.Date().getTime());
			
			FileUploadEntity fileUploadEntity = new FileUploadEntity();
			fileUploadEntity.setCreatedOn(rightNow);
			fileUploadEntity.setFileName(fileName);
			fileUploadEntity.setFileLocation(targetLocation.toString());
			fileUploadEntity.setOwnerId(new Long(1));
			
			fileUploadRepository.save(fileUploadEntity);
		     
		    } 
		    catch (FileAlreadyExistsException e) {
				// TODO: handle exception
		    	System.out.println(e);
			}catch (IOException ex) {
		      //LOG.error("Error in storing file: ", ex);
		      System.out.println(ex);
		    }
			return "File uploaded successfully!! : "+originalFileName;	
	}

	@Override
	public void uploadFiles(List<MultipartFile> files) {
		
			files.forEach(muitipartFile ->{
				File file = null;
				try {
					file = FileUtility.convertMultiPartToFile(muitipartFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String fileName = FileUtility.generateFileName(muitipartFile, "vivek.dubey@xoriant.com");
				uploadToS3(bucketName, fileName, file);
			});
	}

	private void uploadToS3(String bucketName, String fileName, File file) {
				
		amazonClient.putObject(bucketName, fileName, file);
		
		Timestamp rightNow = new Timestamp(new java.util.Date().getTime());

		FileUploadEntity fileUploadEntity = new FileUploadEntity();
		fileUploadEntity.setCreatedOn(rightNow);
		fileUploadEntity.setFileName(file.getName());
		fileUploadEntity.setFileLocation(fileName);
		fileUploadEntity.setOwnerId(new Long(1));
		
		fileUploadRepository.save(fileUploadEntity);
		
	}


	
}